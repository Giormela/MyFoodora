package myFoodora;

import myFoodora.services.*;

public class MyFoodora {
	private static MyFoodora instance;
	
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
		this.managerService = new ManagerService();
		this.customerService = new CustomerService();
		this.restaurantService = new RestaurantService();
		this.courierService = new CourierService();
		this.orderService = new OrderService();
	}
}
