package fr.cs.oose.myFoodora;

import java.util.Collection;
import fr.cs.oose.myFoodora.user.*;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyFoodora {
	private Double serviceFee;
	private Double markupPercentage;
	private Double deliveryCost;
	
	private Map<Integer, User> users;
	private Set<Order> orders;
	
	
	public MyFoodora() {
		super();
		this.users = new HashMap<Integer, User>();
		this.orders = new HashSet<Order>();
		this.serviceFee = 0.0;
		this.markupPercentage = 0.0;
		this.deliveryCost = 0.0;
	}
	
	public MyFoodora(Double serviceFee, Double markupPercentage, Double deliveryCost) {
		super();
		this.serviceFee = serviceFee;
		this.markupPercentage = markupPercentage;
		this.deliveryCost = deliveryCost;
	}	
	
	public void testName() {
		User u = UserBuilder.buildCustomer().addSurname("Rossi").addName("Mario").getResult();
	}
	
}
