package myFoodora.entities;

import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;

public class FidelityCard {
	private Customer customer;
	private Restaurant restaurant;
	private Integer points;
	
	public FidelityCard(Customer customer, Restaurant restaurant) {
		super();
		this.customer = customer;
		this.restaurant = restaurant;
		this.points = 0;
	}
	
	public Double applyDiscount(Double fullPrice) {
		return fullPrice;
	}

	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	public Customer getCustomer() {
		return customer;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}
}
