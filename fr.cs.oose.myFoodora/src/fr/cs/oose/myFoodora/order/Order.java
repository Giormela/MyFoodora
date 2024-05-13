package fr.cs.oose.myFoodora.order;

import java.util.Collection;

import fr.cs.oose.myFoodora.user.*;

public class Order {
	private Customer customer;
	private Restaurant restaurant;
	//private Collection<Food> food;
	private Courier courier;
	private OrderState state;
	
	
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
}
