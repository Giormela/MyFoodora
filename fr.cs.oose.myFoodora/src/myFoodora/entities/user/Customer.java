package myFoodora.entities.user;

import myFoodora.entities.FidelityCard;
import myFoodora.entities.Order;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class Customer extends LocalizedUser {
	private String surname;
	private String email;
	private String phone;
	private Boolean consensus;
	private Set<Order> orderHistory;
	private Set<FidelityCard> fidelityCards;
	
	
	
	Customer() {
		super();
		this.orderHistory = new HashSet<Order>();
		this.fidelityCards = new HashSet<FidelityCard>();
	}
	
	public void addFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCards.add(fidelityCard);
	}
	
	public void removeFidelityCard(Restaurant restaurant) {
		Optional<FidelityCard> optional = this.fidelityCards.stream().filter(fc -> fc.getRestaurant().equals(restaurant)).findAny();
		if (optional.isPresent()) {
			this.fidelityCards.remove(optional.get());
		}
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
