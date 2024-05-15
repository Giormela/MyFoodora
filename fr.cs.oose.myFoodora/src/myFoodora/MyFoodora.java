package myFoodora;

import myFoodora.services.*;

public class MyFoodora {
	private static MyFoodora instance;
	private Double serviceFee;
	private Double markupPercentage;
	private Double deliveryCost;
	
	public UserService userService;
	public OrderService orderService;
	
	public static MyFoodora getInstance() {
		if (instance == null)
			instance = new MyFoodora();
		return instance;
	}
	
	private MyFoodora() {
		super();
		this.serviceFee = 0.0;
		this.markupPercentage = 0.0;
		this.deliveryCost = 0.0;
		this.userService = new UserService();
		this.orderService = new OrderService();
	}
	
}
