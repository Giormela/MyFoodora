package myFoodora.services;

import java.util.HashSet;
import java.util.Set;

import myFoodora.entities.Order;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.OrderState;

public class OrderService {
	private Set<Order> orders;

	public OrderService() {
		super();
		this.orders = new HashSet<Order>();
	}

	public void registerOrder(Customer customer, Restaurant restaurant) {
		Order newOrder = new Order(customer, restaurant);
		//newOrder.setProfit(null);
		customer.addOrder(newOrder);
		restaurant.addOrder(newOrder);
		this.orders.add(newOrder);
	}
	
//	public Double getProfitTimeInterval(Data from, Data to) {
//		return this.orders.stream()
//					.filter(o->o.getState()==OrderState.Delivered)
//					.mapToDouble(Order::getProfit)
//					.sum();
//	}
	
	
}
