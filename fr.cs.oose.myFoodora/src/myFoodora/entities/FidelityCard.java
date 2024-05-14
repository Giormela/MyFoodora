package myFoodora.entities;

import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;

public class FidelityCard {
	private Customer customer;
	private Restaurant restaurant;
	private Integer points;
	
	FidelityCard(Customer customer, Restaurant restaurant) {
		super();
		this.customer = customer;
		this.restaurant = restaurant;
		this.points = 0;
	}
}
