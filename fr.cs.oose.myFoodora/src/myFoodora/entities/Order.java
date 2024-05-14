package myFoodora.entities;

import java.util.Collection;

import myFoodora.MyFoodora;
import myFoodora.entities.user.Courier;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;

public class Order {
	private Customer customer;
	private Restaurant restaurant;
	//private Collection<Food> food;
	private Courier courier;
	private OrderState state;
	private Double profit;
	
	public Order(Customer customer, Restaurant restaurant) {
		super();
		this.customer = customer;
		this.restaurant = restaurant;
		this.state = OrderState.Pending;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Courier getCourier() {
		return courier;
	}
	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	public OrderState getState() {
		return state;
	}
	public void setState(OrderState state) {
		this.state = state;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
}
