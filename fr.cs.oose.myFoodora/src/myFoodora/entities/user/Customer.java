package myFoodora.entities.user;

import myFoodora.entities.Fidelity.FidelityCard;
import myFoodora.entities.Order;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Customer extends LocalizedUser {
	private String surname;
	private String email;
	private String phone;
	private Boolean consent;
	private Map<Restaurant, FidelityCard> fidelityCards;
	
	
	
	public Customer() {
		super();
		this.consent = false;
		this.orderHistory = new HashSet<Order>();
		this.fidelityCards = new HashMap<Restaurant, FidelityCard>();
	}
	
	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Boolean getConsent() {
		return consent;
	}

	public void setConsent(Boolean consensus) {
		this.consent = consensus;
	}

	public Double applyFidelityCard(Restaurant restaurant, Double fullPrice) {
		if(fidelityCards.containsKey(restaurant)) {
			return fidelityCards.get(restaurant).applyDiscount(fullPrice, this);
		}
		return fullPrice;
	}

	public void addFidelityCard(Restaurant restaurant, FidelityCard fidelityCard) {
		this.fidelityCards.putIfAbsent(restaurant, fidelityCard);
	}

	public void removeFidelityCard(Restaurant restaurant) {
		this.fidelityCards.remove(restaurant);
	}

	public Set<Restaurant> getRestaurantsWithFidelityCards() {
		return this.fidelityCards.keySet();
	}

	public FidelityCard getFidelityCardForRestaurant(Restaurant restaurant) {
		return this.fidelityCards.get(restaurant);
	}

}
