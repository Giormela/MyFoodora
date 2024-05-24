package myFoodora.services;

import myFoodora.entities.FidelityCard;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;

public class CustomerService extends UserService<Customer> {
	public void registerFidelityCard(Customer customer, Restaurant restaurant) {
		FidelityCard newFidelityCard = new FidelityCard(customer, restaurant);
		customer.addFidelityCard(newFidelityCard);
		restaurant.addFidelityCard(newFidelityCard);
	}
	
	public void removeFidelityCard(Customer customer, Restaurant restaurant) {
		customer.removeFidelityCard(restaurant);
		restaurant.removeFidelityCard(customer);
	}
}
