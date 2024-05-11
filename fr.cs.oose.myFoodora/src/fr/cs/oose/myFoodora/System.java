package fr.cs.oose.myFoodora;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class System {
	private Map<Integer, User> users;
	private Set<Order> orders;
	private Double serviceFee;
	private Double markupPercentage;
	private Double deliveryCost; 
	
	
	public System() {
		super();
		this.users = new HashMap<Integer, User>();
		this.orders = new HashSet<Order>();
		this.serviceFee = 0.0;
		this.markupPercentage = 0.0;
		this.deliveryCost = 0.0;
	}
	public System(Double serviceFee, Double markupPercentage, Double deliveryCost) {
		super();
		this.serviceFee = serviceFee;
		this.markupPercentage = markupPercentage;
		this.deliveryCost = deliveryCost;
	}	
	
}
