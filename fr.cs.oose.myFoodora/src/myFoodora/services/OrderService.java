package myFoodora.services;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import myFoodora.entities.Date;
import myFoodora.entities.Order;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;

public class OrderService {
	private Set<Order> orders;

	public OrderService() {
		super();
		this.orders = new HashSet<Order>();
	}

	public void registerOrder(Customer customer, Restaurant restaurant) {
		Order newOrder = new Order(customer, restaurant, Date.now());
		//newOrder.setProfit(null);
		customer.addOrder(newOrder);
		restaurant.addOrder(newOrder);
		this.orders.add(newOrder);
	}
	
	private Stream<Order> getOrdersOverTime(Date from, Date to){
		return orders.stream().filter(o->o.getData().isIncluded(from, to));
	}
	
	public Double getTotalProfit(Date from, Date to) {
		return getOrdersOverTime(from, to)
			.mapToDouble(Order::getProfit)
			.sum();
	}
	
	public Double getAverageProfitPerCustomer(Date from, Date to) {
		long numberActiveCustomers = getOrdersOverTime(from, to)
			.map(Order::getCustomer)
			.distinct()
			.count();
		return getTotalProfit(from, to) / numberActiveCustomers;
	}	
}
