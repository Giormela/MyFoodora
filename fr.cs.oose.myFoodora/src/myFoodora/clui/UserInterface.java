package myFoodora.clui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import myFoodora.MyFoodora;
import myFoodora.entities.*;
import myFoodora.entities.food.Dish;
import myFoodora.entities.user.Courier;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.entities.user.User;
import myFoodora.enums.CourierState;
import myFoodora.enums.DeliveryPolicyType;
import myFoodora.enums.DishType;
import myFoodora.enums.FoodCategory;
import myFoodora.enums.PermissionType;
import myFoodora.exceptions.CommandException;
import myFoodora.exceptions.ElementNotFoundException;
import myFoodora.services.UserBuilder;

public class UserInterface {
	protected final Map<String, Command> COMMANDS;
	
	protected Boolean runnning;
	protected BufferedReader input;
	protected BufferedWriter output;
	protected Set<String> allowedCommands;
	
	protected UserInterface(Reader input, Writer output) {
		this.runnning = true;
		this.input = new BufferedReader(input);
		this.output = new BufferedWriter(output);
		this.allowedCommands = new TreeSet<String>();
		this.COMMANDS = initializeCommands();
	}
	
	public static UserInterface createConsoleInterface() {
		Reader input = new InputStreamReader(System.in);
		Writer output = new OutputStreamWriter(System.out);
		return new UserInterface(input, output);
	}
	
	public void renderLoop() {
		printWelcome();
		while (runnning) {
			updateAllowedCommands();
			flush();
			
			try {
				readCommand();
			} catch (CommandException e) {
				print(e.message, Color.RED);
			}
		}
	}
	
	protected User getLoggedUser() throws CommandException{
		MyFoodora app = MyFoodora.getInstance();
		return app.getLoggedUser().orElseThrow(()->new CommandException("A problem has arisen with your account permissions")).getUser();
	}
	protected void printError(String message) {
		print(message, Color.RED);
	}
	protected void printSuccess(String message) {
		print(message, Color.GREEN);
	}
	protected void printSuccess() {
		print("Operation successfully completed", Color.GREEN);
	}
	protected void print(String text, Color color) {
		print(Color.colorText(text, color));
	}
	protected void print(String text) {
		if (text.isEmpty()) return;
		try {
			output.write(text);
			output.newLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	private void printWelcome() {
		String welcome = "**********************************************\n"
                + "*                                            *\n"
                + "*           Welcome to MyFoodora!            *\n"
                + "*                                            *\n"
                + "*  Delivering Happiness, One Meal at a Time  *\n"
                + "*                                            *\n"
                + "**********************************************\n"
                + "\n";
        String assisntance = "Need assistance? Type 'help' to see the list of available commands and get support.";
        print(welcome, Color.CYAN);
        print(assisntance);
	}
	protected static <T extends Display> String displayCollection(Collection<T> list) {
		return list.stream()
				.map(Display::display)
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
	}
	protected void readCommand() throws CommandException {
		String line;
		try {
			line = input.readLine();
		} catch (IOException e) {
			throw new CommandException(e.getMessage());
		}
		
		String[] words = line.split("\\s+");
		String commandName = words[0];
		if (allowedCommands.contains(commandName)) {
			Command command = COMMANDS.get(commandName);
			String[] args = Arrays.copyOfRange(words, 1, words.length);
			command.run(args);
		} else {
			throw new CommandException("No allowed command named "+commandName+" found");
		}
	}
	
	protected void flush() {
		try {
			output.flush();
		} catch (IOException e) {
			print(e.getMessage(), Color.RED);
		}
	}

	
	protected void updateAllowedCommands() {
		allowedCommands.clear();
		Optional<Credential> loggedUser = MyFoodora.getInstance().getLoggedUser();
		
		allowedCommands.addAll(COMMANDS.entrySet().stream().filter(e->e.getValue().permission == null).map(Map.Entry::getKey).toList());
		if (loggedUser.isEmpty()) {
			allowedCommands.remove("profile");
			allowedCommands.remove("logout");
		} else {
			allowedCommands.remove("login");
			allowedCommands.remove("setup");
			allowedCommands.addAll(COMMANDS.entrySet().stream()
					.filter(e->e.getValue().permission == loggedUser.get().getPermission())
					.map(Map.Entry::getKey)
					.toList());
		}
	}
	
	public void setRunnning(Boolean runnning) {
		this.runnning = runnning;
	}

	private class Command {
		private String name;
		private String description;
		private PermissionType permission;
		private Integer numberParameter;
		private CommandFunction function;
		
		private Command(String name, String description, PermissionType permission, Integer numberParameter, CommandFunction function) {
			super();
			this.name = name;
			this.description = description;
			this.permission = permission;
			this.numberParameter = numberParameter;
			this.function = function;
		}
		private Command(String name, String description, Integer numberParameter, CommandFunction function) {
			super();
			this.name = name;
			this.description = description;
			this.permission = null;
			this.numberParameter = numberParameter;
			this.function = function;
		}
		
		protected void run(String[] args) throws CommandException {	
			if (args.length != numberParameter) {
				throw new CommandException("Number of arguments doesn't matches");
			}
			function.run(args);
		}
		protected static <T> T enumFromString(T[] enumValues, String string) throws ElementNotFoundException{
			return Arrays.stream(enumValues)
	    			.filter(dt->dt.toString().toLowerCase().equals(string.toLowerCase()))
	    			.findAny()
	    			.orElseThrow(()->new ElementNotFoundException("DishType not found"));
		}
		@Override
		public String toString() {
			return " "+Color.colorText(name, Color.YELLOW)+" "+description;
		}
	}
	
	private Map<String, Command> initializeCommands(){
		return Stream.of(new Command[] {
				new Command(
						"help", 
						"\n\tSee the list of available commands", 
						0, 
						(args)->{
							allowedCommands.stream()
								.map(c->COMMANDS.get(c).toString())
								.forEach(this::print);
						}),
				new Command(
						"runTest", 
						"<path> \n\tExecute the commands inside the specified file", 
						1, 
						(args)->{
							try {
								UserInterface userInterface = new UserInterfaceFile(args[0]);
								userInterface.renderLoop();
								printSuccess();
							} catch (IOException e) {
								throw new CommandException("Couldn't find the file "+args[0]);					
							}
						}),
				new Command(
						"setup", 
						" \n\tDescription", 
						PermissionType.Manager,
						0, 
						(args)->{
							try {
								UserInterface userInterface = new UserInterfaceFile("Setup.txt");
								userInterface.renderLoop();
								printSuccess();
							} catch (IOException e) {
								throw new CommandException("Couldn't find the file "+args[0]);					
							}
						}),
				new Command("close", 
						" \n\tClose application", 
						0,  
						(agrs)->{
							setRunnning(false);
							try {
								flush();
								input.close();
								output.close();
							} catch (IOException e) {
								throw new CommandException(e.getMessage());						
							}
						}),
				new Command("logout", 
						" \n\tdescription",  
						0, 
						(args)->{
							MyFoodora.getInstance().logout();
							printSuccess();
						}),
				new Command("login", 
						"<username> <password> \n\tdescription", 
						2, 
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Optional<Credential> credential = app.credentialService.tryLogin(args[0], args[1]);
							if (credential.isEmpty()) 
								print("Login failed", Color.YELLOW);
							else {
								app.login(credential);
								printSuccess("Login was successful - Permissions of "+credential.get().getPermission()+" granted");	
								String notificationMessage = displayCollection(getLoggedUser().getNotifications());
								print(notificationMessage, Color.PURPLE);
							}
						}),
				new Command("registerRestaurant", 
						"<name> <address> <username> <password> \n\tRegister a new restaurant in the system", 
						PermissionType.Manager, 
						4, 
						(args)->{
					Restaurant r = UserBuilder.buildUserOfType(Restaurant.class)
							.addName(args[0])
							.addLocation(Location.convertFromAdressToCoordinates(args[1]))
							.addCredential(args[2], args[3])
							.getResult();
					MyFoodora.getInstance().restaurantService.registerUser(r);
					printSuccess();
				}),
				new Command("registerCustomer", 
						"<firstName> <lastName> <username> <position> <password> \n\tRegister a new customer in the system", 
						null, 
						5, 
						(args)->{
							Customer c = UserBuilder.buildUserOfType(Customer.class)
									.addName(args[0])
									.addSurname(args[1])
									.addCredential(args[2], args[4])
									.addLocation(Location.convertFromAdressToCoordinates(args[3]))
									.getResult();
							MyFoodora.getInstance().customerService.registerUser(c);
							printSuccess();
						}),
				new Command("registerCourier", 
						"<firstName> <lastName> <username> <position> <password> \n\tRegister a new courier in the system", 
						PermissionType.Manager, 
						5, 
						(args)->{
							Courier c = UserBuilder.buildUserOfType(Courier.class)
									.addName(args[0])
									.addSurname(args[1])
									.addCredential(args[2], args[4])
									.addLocation(Location.convertFromAdressToCoordinates(args[3]))
									.getResult();
							MyFoodora.getInstance().courierService.registerUser(c);
							printSuccess();
						}),
				new Command("addDishRestaurantMenu", 
						"<dishName> <dishCategory> <foodCategory> <unitPrice> \n\tAdd a new dish to your menu", 
						PermissionType.Restaurant, 
						4, 
						(args)->{
							Restaurant r = (Restaurant)getLoggedUser();
							Dish d = new Dish(
									r, 
									args[0], 
									Command.enumFromString(DishType.values(), args[1]), 
									Command.enumFromString(FoodCategory.values(), args[2]),
									Double.valueOf(args[3])
							);
							
							r.addDish(d);
							printSuccess();

						}),
				new Command("setSpecialOffer",
						" <mealName>\n\tDESCRIPTION",
						PermissionType.Restaurant,
						1,
						(args)->{
							Restaurant r = (Restaurant)getLoggedUser();
							
							r.setMealOfTheWeek(args[0]);
							printSuccess();
						}),
				new Command("removeFromSpecialOffer",
						" <mealName>\n\tDESCRIPTION",
						PermissionType.Restaurant,
						1,
						(args)->{
							Restaurant r = (Restaurant)getLoggedUser();
							
							r.setNotMealOfTheWeek(args[0]);
							printSuccess();
						}),
				new Command("createOrder",
						" <restaurantName> <orderName>\n\tDESCRIPTION",
						PermissionType.Customer,
						2,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Customer c = (Customer)getLoggedUser();
							Restaurant r = app.restaurantService.getUserByName(args[0]);
							Order newOrder = new Order(c, r, args[1]);
							
							c.prepareNewOrder(newOrder);
							printSuccess();
						}),
				new Command("addItem2Order",
						" <orderName> <itemName>\n\tDESCRIPTION",
						PermissionType.Customer,
						2,
						(args)->{
							Customer c = (Customer)getLoggedUser();
							
							c.addFoodToOrder(args[0], args[1]);
							printSuccess();
						}),
				new Command("endOrder",
						" <orderName> <date>\n\tDESCRIPTION",
						PermissionType.Customer,
						2,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Customer c = (Customer)getLoggedUser();
							
							Order payedOrder = c.payOrder(args[0], Date.from(args[1]));
							app.orderService.registerOrder(payedOrder);
							printSuccess();
						}),
				new Command("showRestaurants",
						" \n\tDESCRIPTION",
						PermissionType.Customer,
						0,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							StringBuilder sb = app.restaurantService.getList().stream()
								.map(Restaurant::display)
								.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);
							print(sb.toString(), Color.CYAN);
						}),
				new Command("showTotalProfit",
						" \n\tDESCRIPTION",
						PermissionType.Manager,
						0,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Double profit = app.orderService.getTotalProfit();
							print("The total profit is "+profit.toString(), Color.CYAN);
						}),
				new Command("showTotalProfitBetween",
						"<startDate> <endDate> \n\tDESCRIPTION",
						PermissionType.Manager,
						2,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Date from = Date.from(args[0]);
							Date to = Date.from(args[1]);
							Double profit = app.orderService.getTotalProfit(from, to);
							print("The profit between "+from.toString()+" and "+to.toString()+" is equal to "+profit.toString(), Color.CYAN);
						}),
				new Command("createMeal",
						" <mealName>\n\tDESCRIPTION",
						PermissionType.Restaurant,
						1,
						(args)->{
							Restaurant r = (Restaurant)getLoggedUser();
							
							r.createMeal(args[0]);
							printSuccess();
						}),
				new Command("addDish2Meal",
						" <dishName> <mealName>\n\tDESCRIPTION",
						PermissionType.Restaurant,
						2,
						(args)->{
							Restaurant r = (Restaurant)getLoggedUser();
							
							r.addDishToMeal(args[1], args[0]);
							printSuccess();
						}),
				new Command("showMeal",
						" <mealName>\n\tDESCRIPTION",
						PermissionType.Restaurant,
						1,
						(args)->{
							Restaurant r = (Restaurant)getLoggedUser();
							
							print(r.displayMeal(args[0]), Color.CYAN);
						}),
				new Command("saveMeal",
						" <mealName>\n\tDESCRIPTION",
						PermissionType.Restaurant,
						1,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Restaurant r = (Restaurant)getLoggedUser();
							
							r.saveMeal(args[0]);
							Notification notification = new Notification(r.getName()+" has published the new meal "+args[0]);
							app.customerService.sendNotifications(notification);
							printSuccess();
						}),
				new Command("onDuty",
						" \n\tDESCRIPTION",
						PermissionType.Courier,
						0,
						(args)->{
							Courier c = (Courier)getLoggedUser();
							
							c.setState(CourierState.OnDuty);
							printSuccess();
						}),
				new Command("offDuty",
						" \n\tDESCRIPTION",
						PermissionType.Courier,
						0,
						(args)->{
							Courier c = (Courier)getLoggedUser();
							
							c.setState(CourierState.OffDuty);
							printSuccess();
						}),
				new Command("findDeliverer",
						" \n\tDESCRIPTION",
						PermissionType.Manager,
						0,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();							
							for (Order pendingOrder : app.orderService.getPendingOrders()) {
								app.courierService.assignOrderToBestCourier(pendingOrder);
							}
							printSuccess();
						}),
				new Command("profile",
						" \n\tShow your profile",
						0,
						(args)->{
							print(getLoggedUser().display(), Color.CYAN);
						}),
				new Command("showRestaurantTop",
						" \n\tdescription",
						PermissionType.Manager,
						0,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							print(displayCollection(app.restaurantService.getTopRestaurant()), Color.CYAN);
						}),
				new Command("showCustomer",
						" \n\tdescription",
						PermissionType.Manager,
						0,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							print(displayCollection(app.customerService.getList()), Color.CYAN);
						}),
				new Command("showCouriersTop",
						" \n\tdescription",
						PermissionType.Manager,
						0,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							print(displayCollection(app.courierService.getTopCouriers()), Color.CYAN);
						}),
				new Command("setDeliveryPolicy",
						"<delPolicyName> \n\tdescription",
						PermissionType.Manager,
						1,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							DeliveryPolicyType deliveryPolicy = Command.enumFromString(DeliveryPolicyType.values(), args[0]);
							app.courierService.selectDeliveryPolicy(deliveryPolicy);
							printSuccess();
						}),
		}).collect(Collectors.toMap(c->c.name, c->c));
	}
}
