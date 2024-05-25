package myFoodora.clui;

import java.awt.MultipleGradientPaint.CycleMethod;
import java.io.PrintStream;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import myFoodora.MyFoodora;
import myFoodora.entities.*;
import myFoodora.entities.food.Dish;
import myFoodora.entities.user.Courier;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.entities.user.User;
import myFoodora.enums.DishType;
import myFoodora.enums.PermissionType;
import myFoodora.exceptions.CommandException;
import myFoodora.exceptions.UserRegistrationException;
import myFoodora.services.UserBuilder;

public class UserInterface {
	private final Map<String, Command> COMMANDS;
	
	private Boolean runnning;
	private Scanner input;
	private PrintStream output;
	private Set<String> allowedCommands;

	public UserInterface() {
		this.runnning = true;
		this.input = new Scanner(System.in);
		this.output = System.out;
		this.allowedCommands = new TreeSet<String>();
		this.COMMANDS = initializeCommands();
	}

	
	public void renderLoop() {
		printWelcome();
		while (runnning) {
			updateAllowedCommands();
			
			try {
				readCommand();
			} catch (CommandException e) {
				print(e.message, Color.RED);
			}
		}
	}
	
	private void print(String text, Color color) {
		output.println(Color.colorText(text, color));
	}
	
	private void print(String text) {
		output.println(text);
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
	
	private void readCommand() throws CommandException {
		String commandName = input.next();
		
		if (allowedCommands.contains(commandName)) {
			Command command = COMMANDS.get(commandName);
			String[] args = command.readArguments();
			command.run(args);
		} else {
			throw new CommandException("No allowed command named "+commandName+" found");
		}
	}

	
	private void updateAllowedCommands() {
		allowedCommands.clear();
		Optional<User> loggedUser = MyFoodora.getInstance().getLoggedUser();
		
		allowedCommands.add("help");
		allowedCommands.add("close");
		if (loggedUser.isEmpty()) {
			allowedCommands.add("login");
		} else {
			allowedCommands.add("profile");
			allowedCommands.add("logout");
			allowedCommands.addAll(
					COMMANDS.entrySet().stream()
					.filter(e->e.getValue().permission == loggedUser.get().getCredential().getPermission())
					.map(Map.Entry::getKey)
					.toList());
		}
	}
	
	public void setRunnning(Boolean runnning) {
		this.runnning = runnning;
	}

	class Command {
		private String name;
		private String description;
		private PermissionType permission;
		private Integer numberParameter;
		private Consumer<String[]> function;
		
		private Command(String name, String description, PermissionType permission, Integer numberParameter, Consumer<String[]> function) {
			super();
			this.name = name;
			this.description = description;
			this.permission = permission;
			this.numberParameter = numberParameter;
			this.function = function;
		}
		private Command(String name, String description, Integer numberParameter, Consumer<String[]> function) {
			super();
			this.name = name;
			this.description = description;
			this.permission = null;
			this.numberParameter = numberParameter;
			this.function = function;
		}
		
		
		private String[] readArguments() throws CommandException {
			String[] args = new String[numberParameter];
			for (int i = 0; i < numberParameter; i++) {
				try {
					args[i] = input.next();
				} catch (NoSuchElementException  e) {
					throw new CommandException("Error in reading arguments");
				}
			}
			return args;
		}
		
		private  void run(String[] args) {
			function.accept(args);
		}
		
		public String toString() {
			return Color.colorText(name, Color.YELLOW)+" "+description;
		}
	}
	
	private Map<String, Command> initializeCommands(){
		return Stream.of(new Command[] {
				new Command("help", 
						"<> \n\tSee the list of available commands", 
						0, 
						(args)->allowedCommands.stream()
							.map(c->COMMANDS.get(c).toString())
							.forEach(this::print)
						),
				new Command("close", 
						"<> \n\tdescription", 
						0,  
						(agrs)->this.setRunnning(false)),
				new Command("logout", 
						"<> \n\tdescription",  
						0, 
						(args)->MyFoodora.getInstance().logout()),
				new Command("login", 
						"<> \n\tdescription", 
						2, 
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Optional<User> user = app.credentialService.tryLogin(args[0], args[1]);
							if (user.isEmpty()) 
								print("Login failed", Color.YELLOW);
							else {
								print("Login was successful - Permissions of "+user.get().getCredential().getPermission()+" granted", Color.GREEN);
								print("Hi "+user.get().getName()+"!");
							}
							app.login(user);
						}),
				new Command("registerRestaurant", 
						"<> \n\tdescription", 
						PermissionType.Manager, 
						4, 
						(args)->{
					Restaurant r = UserBuilder.buildUserOfType(Restaurant.class)
							.addName(args[0])
							.addLocation(Location.convertFromAdressToCoordinates(args[1]))
							.addCredential(args[2], args[3])
							.getResult();
					try {
						MyFoodora.getInstance().restaurantService.registerUser(r);
					} catch (UserRegistrationException e) {
						print(e.message, Color.RED);
					}
				}),
				new Command("registerCustomer", 
						"<> \n\tdescription", 
						null, 
						4, 
						(args)->{
							Customer c = UserBuilder.buildUserOfType(Customer.class)
									.addName(args[0])
									.addSurname(args[1])
									.addCredential(args[2], args[4])
									.addLocation(Location.convertFromAdressToCoordinates(args[3]))
									.getResult();
							try {
								MyFoodora.getInstance().customerService.registerUser(c);
							} catch (UserRegistrationException e) {
								print(e.message, Color.RED);
							}
						}),
				new Command("registerCourier", 
						"<> \n\tdescription", 
						PermissionType.Manager, 
						4, 
						(args)->{
							Courier c = UserBuilder.buildUserOfType(Courier.class)
									.addName(args[0])
									.addSurname(args[1])
									.addCredential(args[2], args[4])
									.addLocation(Location.convertFromAdressToCoordinates(args[3]))
									.getResult();
							try {
								MyFoodora.getInstance().courierService.registerUser(c);
							} catch (UserRegistrationException e) {
								print(e.message, Color.RED);
							}
						}),
				new Command("addDishRestaurantMenu", 
						"<> \n\tdescription", 
						PermissionType.Restaurant, 
						4, 
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Dish d = new Dish(args[0], Double.valueOf(args[3]), DishType.fromString(args[1]));
							try {
								Restaurant r = (Restaurant) app.getLoggedUser().get();
								r.addDish(d);
								print("Dish added", Color.GREEN);
							} catch (NoSuchElementException | ClassCastException e) {
								print("Unable to get information from logged Restaurant", Color.RED);
							} 
						}),
				new Command("profile",
						"\n\tShow your profile",
						0,
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							print(app.getLoggedUser().get().display(), Color.CYAN);
						}),
				
		}).collect(Collectors.toMap(c->c.name, c->c));
	}
}
