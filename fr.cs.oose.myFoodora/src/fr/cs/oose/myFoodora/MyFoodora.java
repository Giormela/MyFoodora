package fr.cs.oose.myFoodora;

import fr.cs.oose.myFoodora.order.*;
import fr.cs.oose.myFoodora.user.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyFoodora {
	private static MyFoodora instance;
	
	private Double serviceFee;
	private Double markupPercentage;
	private Double deliveryCost;
	
	private Map<Integer, User> users;
	private Set<Order> orders;
	
	public static MyFoodora getInstance() {
		if (instance == null)
			instance = new MyFoodora();
		return instance;
	}
	
	private MyFoodora() {
		super();
		this.users = new HashMap<Integer, User>();
		this.orders = new HashSet<Order>();
		this.serviceFee = 0.0;
		this.markupPercentage = 0.0;
		this.deliveryCost = 0.0;
	}
	
}
