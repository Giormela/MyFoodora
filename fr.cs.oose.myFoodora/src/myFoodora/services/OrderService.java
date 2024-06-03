package myFoodora.services;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import myFoodora.MyFoodora;
import myFoodora.entities.Date;
import myFoodora.entities.Order;
import myFoodora.enums.OrderState;

public class OrderService {
	private Double serviceFee;
	private Double markupPercentage;
	private Double deliveryCost;
	private Double targetProfit;
	private Set<Order> orders;

	public OrderService() {
		super();
		this.serviceFee = 1.0;
		this.markupPercentage = 5.0;
		this.deliveryCost = 1.0;
		this.orders = new HashSet<Order>();
	}
	
	public Collection<Order> getPendingOrders() {
		return orders.stream().filter(o->o.getState().equals(OrderState.Pending)).toList();
	}

	/**
	 * Registers a new order.
	 *
	 * This method creates a new Order object with the provided customer, restaurant, and collection of buyable items (food). It also calculates the profit for the order.
	 *
	 * @param customer the customer who placed the order
	 * @param restaurant the restaurant that will fulfill the order
	 * @param food a collection of buyable items constituting the order
	 * @throws NullPointerException if any of the arguments are null
	 */
	public void registerOrder(Order newOrder) {
		setProfitToOrder(newOrder);
		newOrder.getCustomer().addOrderToHistory(newOrder);
		newOrder.getRestaurant().addOrderToHistory(newOrder);
		this.orders.add(newOrder);
	}
	
	/**
	 * Calculates and sets the profit for a given order.
	 *
	 * @param order the order for which to calculate the profit
	 * @throws NullPointerException if the order argument is null
	 */
	private void setProfitToOrder(Order order) {
		Double orderPrice = order.getPricePayed();
		Double orderProfit = orderPrice * markupPercentage + serviceFee - deliveryCost;
		order.setProfit(orderProfit);
	}
	
	/**
	 * Returns a stream of orders within a specific date range.
	 *
	 * @param from the start date of the range (inclusive)
	 * @param to the end date of the range (inclusive)
	 * @return a stream of orders that fall within the provided date range
	 * @throws NullPointerException if either of the date arguments (`from` or `to`) is null
	 */
	private Stream<Order> getOrdersInRange(Date from, Date to){
		return orders.stream().filter(o->o.getData().isIncluded(from, to));
	}
	
	/**
	 * Calculates the total profit for orders within a specific date range.
	 *
	 * This method retrieves a stream of orders for the specified date range using the `getOrdersInRange` method. It then extracts the profit for each order using the `getProfit` method of the `Order` class and sums them up using the `sum` method on the stream. The returned value represents the total profit generated from all orders placed between the provided `from` and `to` dates (inclusive).
	 *
	 * @param from the start date of the range (inclusive)
	 * @param to the end date of the range (inclusive)
	 * @return the total profit for all orders within the specified date range
	 * @throws NullPointerException if either of the date arguments (`from` or `to`) is null
	 */
	public Double getTotalProfit(Date from, Date to) {
		return getOrdersInRange(from, to)
			.mapToDouble(Order::getProfit)
			.sum();
	}
	
	/**
	 * Calculates the total profit for orders.
	 * 
	 * @return the total profit for all orders
	 * @throws NullPointerException if either of the date arguments (`from` or `to`) is null
	 */
	public Double getTotalProfit() {
		return orders.stream()
			.mapToDouble(Order::getProfit)
			.sum();
	}
	
	/**
	 * Calculates the average profit per customer within a specified date range.
	 *
	 * @param from the start date of the date range (inclusive)
	 * @param to the end date of the date range (inclusive)
	 * @return the average profit per customer as a Double, or null if there are no active customers in the date range
	 * @throws IllegalArgumentException if from date is after to date
	 */
	public Double getAverageProfitPerCustomer(Date from, Date to) {
		long numberActiveCustomers = getOrdersInRange(from, to)
			.map(Order::getCustomer)
			.distinct()
			.count();
		return getTotalProfit(from, to) / numberActiveCustomers;
	}	
	
	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public void setMarkupPercentage(Double markupPercentage) {
		this.markupPercentage = markupPercentage;
	}

	public void setDeliveryCost(Double deliveryCost) {
		this.deliveryCost = deliveryCost;
	}

	public Double getTargetProfit() {
		return targetProfit;
	}

	public void setTargetProfit(Double targetProfit) {
		this.targetProfit = targetProfit;
	}
	
	public Double getTargetProfit_DeliveryCost(Double serviceFee, Double markupPercentage) {
		long numberOfOrders = getOrdersInRange(Date.oneMonthAgo(), Date.now()).count();
		return (
				getTotalProfit(Date.oneMonthAgo(), Date.now()) * markupPercentage 
				+ numberOfOrders * serviceFee 
				- targetProfit 
				) 
				/ numberOfOrders;
	}
	
	public Double getTargetProfit_ServiceFee(Double markupPercentage, Double deliveryCost) {
		long numberOfOrders = getOrdersInRange(Date.oneMonthAgo(), Date.now()).count();
		return (
				targetProfit
				- getTotalProfit(Date.oneMonthAgo(), Date.now()) * markupPercentage
				+ numberOfOrders * deliveryCost
				) 
				/ numberOfOrders;
	}
	
	public Double getTargetProfit_Markup(Double serviceFee, Double deliveryCost) {
		long numberOfOrders = getOrdersInRange(Date.oneMonthAgo(), Date.now()).count();
		return (
				targetProfit
				- numberOfOrders * serviceFee
				+ numberOfOrders * deliveryCost
				) 
				/ getTotalProfit(Date.oneMonthAgo(), Date.now());
	}

	
	
}
