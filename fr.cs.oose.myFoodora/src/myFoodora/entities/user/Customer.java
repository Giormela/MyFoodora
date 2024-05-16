package myFoodora.entities.user;

import myFoodora.entities.FidelityCard;
import myFoodora.entities.Order;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Customer extends LocalizedUser {
	private String surname;
	private String email;
	private String phone;
	private Boolean consensus;
	private Set<Order> orderHistory;
	private Map<Restaurant, FidelityCard> fidelityCards;
	
	
	
	public Customer() {
		super();
		this.orderHistory = new HashSet<Order>();
		this.fidelityCards = new HashMap<Restaurant, FidelityCard>();
	}
	
	public void addFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCards.putIfAbsent(fidelityCard.getRestaurant(), fidelityCard);
	}
	
	public void removeFidelityCard(Restaurant restaurant) {
		this.fidelityCards.remove(restaurant);
	}
	
	public void addOrder(Order order) {
		this.orderHistory.add(order);
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


	public Boolean getConsensus() {
		return consensus;
	}


	public void setConsensus(Boolean consensus) {
		this.consensus = consensus;
	}
	
	
	
	
}
