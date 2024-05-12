package fr.cs.oose.myFoodora.user;

import fr.cs.oose.myFoodora.food.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;



public class Restaurant extends LocalizedUser {
	private Double genericDiscountFactor;
	private Double specialDiscountFactor;
//	private Map<String, Dish> menu;
//	private Map<String, Meal> meals;
	
	
	Restaurant() {
		super();
		this.genericDiscountFactor = 5.0;
		this.specialDiscountFactor = 10.0;
//		this.menu = new ArrayList<Dish>();
//		this.meals = new ArrayList<Meal>();
	}
	
//	public void addDish(Dish newDish) {
//		this.menu.insert(newDish.getName(), newDish);
//	}
	
//	public void removeDish(String name) {
//		if (menu.contains(name))
//			menu.remove(name);
//	}

	public Double getGenericDiscountFactor() {
		return genericDiscountFactor;
	}

	public void setGenericDiscountFactor(Double genericDiscountFactor) {
		this.genericDiscountFactor = genericDiscountFactor;
	}

	public Double getSpecialDiscountFactor() {
		return specialDiscountFactor;
	}

	public void setSpecialDiscountFactor(Double specialDiscountFactor) {
		this.specialDiscountFactor = specialDiscountFactor;
	}
}