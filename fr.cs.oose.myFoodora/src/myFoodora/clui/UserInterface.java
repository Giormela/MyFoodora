package myFoodora.clui;

import java.util.HashSet;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import myFoodora.MyFoodora;
import myFoodora.entities.*;
import myFoodora.entities.user.Courier;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.entities.user.User;
import myFoodora.enums.PermissionType;
import myFoodora.exceptions.CommandException;
import myFoodora.exceptions.UserRegistrationException;
import myFoodora.services.UserBuilder;

public class UserInterface {
	private Boolean runnning;
	private Scanner scanner;	
	private Set<String> allowedCommands;

	public UserInterface() {
		this.runnning = true;
		this.scanner = new Scanner(System.in);
		this.allowedCommands = new HashSet<String>();
	}

	
	public void renderLoop() {
		while (runnning) {
			updateAllowedCommands();
			
			renderUI();
			
			try {
				readCommand();
			} catch (CommandException e) {
				System.out.println(colorText(e.message, Color.RED));
			}
		}
	}
	
	private void renderUI() {
		Optional<User> loggedUser = MyFoodora.getInstance().getLoggedUser();
		if (loggedUser.isEmpty()) {
			System.out.println(colorText("\t\tWelcome to MyFoodora!", Color.BLUE));
			System.out.println("Digit 'help' to see the list of possible commands");
		} else {
			System.out.println(colorText("\t\tWelcome!", Color.BLUE));
		}
	}
	
	private void readCommand() throws CommandException{
		String commandName = scanner.next();
		
		if (commandName.equals("help")) {
			allowedCommands.stream()
				.map(c->commands.get(c).toString())
				.forEach(System.out::print);
		} else {
			if (allowedCommands.contains(commandName)) {
				Command command = commands.get(commandName);
				Integer n = command.getInputParameters();
				String[] args = new String[n];
				for (int i = 0; i < n; i++) {
					try {
						args[i] = scanner.next();
					} catch (NoSuchElementException  e) {
						throw new CommandException("Error in reading arguments");
					}
				}
				command.getCommand().accept(args);
			} else {
				throw new CommandException("No allowed command named "+commandName+" found");
			}
		}
	}
	
	private void updateAllowedCommands() {
		allowedCommands.clear();
		Optional<User> loggedUser = MyFoodora.getInstance().getLoggedUser();
		
		allowedCommands.add("close");
		if (loggedUser.isEmpty()) {
			allowedCommands.add("login");
		} else {
			allowedCommands.add("logout");
			allowedCommands.addAll(
					commands.entrySet().stream()
					.filter(e->e.getValue().getPermission() == loggedUser.get().getCredential().getPermission())
					.map(Map.Entry::getKey)
					.toList());
		}
	}
	
	public void setRunnning(Boolean runnning) {
		this.runnning = runnning;
	}
	
	public static String colorText(String testo, Color colore) {
        return colore.getColorCode() + testo + "\033[0m";
    }

	private static final Map<String, Command> commands = Stream.of(new Command[] {
			new Command("close", "description", null, 0, (agrs)->MyFoodora.getInstance().close()),
			new Command("logout", "description", null, 0, (args)->MyFoodora.getInstance().logout()),
			new Command("login", "description", null, 2, (args)->{
				MyFoodora app = MyFoodora.getInstance();
				Optional<User> user = app.credentialService.tryLogin(args[0], args[1]);
				if (user.isEmpty()) 
					System.out.println(colorText("Login failed", Color.YELLOW));
				else 
					System.out.println(colorText("Login was successful", Color.GREEN));
				app.login(user);
			}),
			new Command("registerRestaurant", "description", PermissionType.Manager, 4, (args)->{
				Restaurant restaurant = UserBuilder.buildUserOfType(Restaurant.class)
						.addName(args[0])
						.addLocation(Location.convertFromAdressToCoordinates(args[1]))
						.addCredential(args[2], args[3])
						.getResult();
				try {
					MyFoodora.getInstance().restaurantService.registerUser(restaurant);
				} catch (UserRegistrationException e) {
					System.out.println(colorText(e.message, Color.RED));
				}
			}),
			new Command("registerCustomer", "description", null, 4, (args)->{
				Customer customer = UserBuilder.buildUserOfType(Customer.class)
						.addName(args[0])
						.addSurname(args[1])
						.addCredential(args[2], args[4])
						.addLocation(Location.convertFromAdressToCoordinates(args[3]))
						.getResult();
				try {
					MyFoodora.getInstance().customerService.registerUser(customer);
				} catch (UserRegistrationException e) {
					System.out.println(colorText(e.message, Color.RED));
				}
			}),
			new Command("registerCourier", "description", PermissionType.Manager, 4, (args)->{
				Courier courier = UserBuilder.buildUserOfType(Courier.class)
						.addName(args[0])
						.addSurname(args[1])
						.addCredential(args[2], args[4])
						.addLocation(Location.convertFromAdressToCoordinates(args[3]))
						.getResult();
				try {
					MyFoodora.getInstance().courierService.registerUser(courier);
				} catch (UserRegistrationException e) {
					System.out.println(colorText(e.message, Color.RED));
				}
			})
	}).collect(Collectors.toMap(c->c.getName(), c->c));
}
