package myFoodora.clui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
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
		Optional<User> loggedUser = MyFoodora.getInstance().getLoggedUser();
		
		allowedCommands.add("help");
		allowedCommands.add("close");
		allowedCommands.add("file");
		if (loggedUser.isEmpty()) {
			allowedCommands.add("login");
			allowedCommands.add("registerCustomer");
		} else {
			allowedCommands.add("profile");
			allowedCommands.add("logout");
			allowedCommands.addAll(
					COMMANDS.entrySet().stream()
					.filter(e->e.getValue().permission == loggedUser.get().getCredential().getPermission() || e.getValue().permission == null)
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
		
		
//		private String[] readArguments() throws CommandException {
//			String[] args = new String[numberParameter];
//			for (int i = 0; i < numberParameter; i++) {
//				try {
//					args[i] = input.next();
//				} catch (NoSuchElementException  e) {
//					throw new CommandException("Error in reading arguments");
//				}
//			}
//			return args;
//		}
		
		private void run(String[] args) throws CommandException {
			if (args.length != numberParameter) {
				throw new CommandException("Number of arguments doesn't matches");
			}
			function.accept(args);
		}
		
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
						"file", 
						"<path> \n\tExecute the commands inside the specified file", 
						1, 
						(args)->{
							try {
								UserInterface userInterface = new UserInterfaceFile(args[0]);
								userInterface.renderLoop();
								printSuccess();
							} catch (IOException e) {
								printError(e.getMessage());							
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
								printError(e.getMessage());
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
							Optional<User> user = app.credentialService.tryLogin(args[0], args[1]);
							if (user.isEmpty()) 
								print("Login failed", Color.YELLOW);
							else {
								printSuccess("Login was successful - Permissions of "+user.get().getCredential().getPermission()+" granted");
								print("Hi "+user.get().getName()+"!");
							}
							app.login(user);
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
					try {
						MyFoodora.getInstance().restaurantService.registerUser(r);
						printSuccess();
					} catch (UserRegistrationException e) {
						printError(e.message);
					}
				}),
				new Command("registerCustomer", 
						"<firstName> <lastName> <username> <position> <password> \n\tRegister a new customer in the system", 
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
								printSuccess();
							} catch (UserRegistrationException e) {
								printError(e.message);
							}
						}),
				new Command("registerCourier", 
						"<firstName> <lastName> <username> <position> <password> \n\tRegister a new courier in the system", 
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
								printSuccess();
							} catch (UserRegistrationException e) {
								printError(e.message);
							}
						}),
				new Command("addDishRestaurantMenu", 
						"<dishName> <dishCategory> <foodCategory> <unitPrice> \n\tAdd a new dish to your menu", 
						PermissionType.Restaurant, 
						4, 
						(args)->{
							MyFoodora app = MyFoodora.getInstance();
							Dish d = new Dish(args[0], Double.valueOf(args[3]), DishType.fromString(args[1]));
							try {
								Restaurant r = (Restaurant) app.getLoggedUser().get();
								r.addDish(d);
								printSuccess();
							} catch (NoSuchElementException | ClassCastException e) {
								printError(e.getMessage());
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
