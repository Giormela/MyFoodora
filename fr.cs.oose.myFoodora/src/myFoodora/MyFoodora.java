package myFoodora;

import java.util.Optional;

import myFoodora.clui.UserInterface;
import myFoodora.entities.user.User;
import myFoodora.services.*;

public class MyFoodora {
	private static MyFoodora instance;
	
	private UserInterface userInterface;
	private Optional<User> loggedUser;
	public CredentialService credentialService;
	public ManagerService managerService;
	public CustomerService customerService;
	public RestaurantService restaurantService;
	public CourierService courierService;
	public OrderService orderService;
	
	public static MyFoodora getInstance() {
		if (instance == null)
			instance = new MyFoodora();
		return instance;
	}
	
	private MyFoodora() {
		super();
		this.loggedUser = Optional.empty();
		this.userInterface = new UserInterface();
		this.credentialService = new CredentialService();
		this.managerService = new ManagerService();
		this.customerService = new CustomerService();
		this.restaurantService = new RestaurantService();
		this.courierService = new CourierService();
		this.orderService = new OrderService();
	}
	
	public void run() {
		userInterface.renderLoop();
	}
	
	public Optional<User> getLoggedUser() {
		return loggedUser;
	}
	
	public void login(Optional<User> user) {
		loggedUser = user;
	}
	
	public void logout() {
		loggedUser = Optional.empty();
	}
	
	public void close() {
		userInterface.setRunnning(false);
	}
	
}
