package myFoodora;

import java.util.Optional;

import myFoodora.clui.UserInterface;
import myFoodora.entities.Credential;
import myFoodora.entities.user.Manager;
import myFoodora.exceptions.UserRegistrationException;
import myFoodora.services.*;

public class MyFoodora {
	private static MyFoodora instance;

	private Optional<Credential> loggedUser;
	
	public CredentialService credentialService;
	public ManagerService managerService;
	public CustomerService customerService;
	public RestaurantService restaurantService;
	public CourierService courierService;
	public OrderService orderService;
	
	public static MyFoodora getInstance() {
		if (instance == null) {
			instance = new MyFoodora();
		}	
		return instance;
	}
	
	private MyFoodora() {
		super();
		this.loggedUser = Optional.empty();
		this.credentialService = new CredentialService();
		this.managerService = new ManagerService();
		this.customerService = new CustomerService();
		this.restaurantService = new RestaurantService();
		this.courierService = new CourierService();
		this.orderService = new OrderService();
	}
	
	private void setup() {
		Manager m = UserBuilder.buildUserOfType(Manager.class)
				.addName("Giovanni")
				.addSurname("Vitelli")
				.addCredential("ceo", "123456789")
				.getResult();
		try {
			managerService.registerUser(m);
		} catch (UserRegistrationException e) {}
	}
	
	public void run() {
		setup();
		UserInterface userInterface = UserInterface.createConsoleInterface();
		userInterface.renderLoop();
	}
	
	public Optional<Credential> getLoggedUser() {
		return loggedUser;
	}
	
	public void login(Optional<Credential> credential) {
		loggedUser = credential;
	}
	
	public void logout() {
		loggedUser = Optional.empty();
	}
		
}
