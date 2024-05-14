package myFoodora.services;

import java.util.HashSet;
import java.util.Set;

import myFoodora.entities.order.Order;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;

public class OrderService {
	private Set<Order> orders;
	private Set<Order> pendingOrders;

	public OrderService() {
		super();
		this.orders = new HashSet<Order>();
		this.pendingOrders = new HashSet<Order>();
	}

	public void registerOrder(Customer customer, Restaurant restaurant) {
		Order newOrder = new Order(customer, restaurant);
		customer.addOrder(newOrder);
		restaurant.addOrder(newOrder);
		this.orders.add(newOrder);
		this.pendingOrders.add(newOrder);
	}
	
	
}
