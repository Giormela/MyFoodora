package myFoodora.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import myFoodora.entities.Date;
import myFoodora.entities.Order;
import myFoodora.entities.food.Buyable;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;

public class OrderService {
	private Double serviceFee;
	private Double markupPercentage;
	private Double deliveryCost;
	private Set<Order> orders;

	public OrderService() {
		super();
		this.serviceFee = 0.0;
		this.markupPercentage = 0.0;
		this.deliveryCost = 0.0;
		this.orders = new HashSet<Order>();
	}

	public void registerOrder(Customer customer, Restaurant restaurant, Collection<Buyable> food) {
		Order newOrder = new Order(customer, restaurant, food);
		setProfitToOrder(newOrder);
		customer.addOrder(newOrder);
		restaurant.addOrder(newOrder);
		this.orders.add(newOrder);
	}
	
	private void setProfitToOrder(Order order) {
		Double orderPrice = order.getPrice();
		Double orderProfit = orderPrice * markupPercentage + serviceFee - deliveryCost;
		order.setProfit(orderProfit);
	}
	
	private Stream<Order> getOrdersInRange(Date from, Date to){
		return orders.stream().filter(o->o.getData().isIncluded(from, to));
	}
	
	public Double getTotalProfit(Date from, Date to) {
		return getOrdersInRange(from, to)
			.mapToDouble(Order::getProfit)
			.sum();
	}
	
	public Double getAverageProfitPerCustomer(Date from, Date to) {
		long numberActiveCustomers = getOrdersInRange(from, to)
			.map(Order::getCustomer)
			.distinct()
			.count();
		return getTotalProfit(from, to) / numberActiveCustomers;
	}	
}
