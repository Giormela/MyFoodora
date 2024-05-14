package myFoodora.entities.user;

import myFoodora.entities.order.Order;

import java.util.HashSet;
import java.util.Set;

public class Customer extends LocalizedUser {
	private String surname;
	private String email;
	private String phone;
	private Boolean consensus;
	private Set<Order> orderHistory;
	
	
	
	Customer() {
		super();
		this.orderHistory = new HashSet<Order>();
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
