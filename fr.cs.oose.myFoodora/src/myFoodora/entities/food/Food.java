package myFoodora.entities.food;

import myFoodora.entities.user.Restaurant;

abstract public class Food {
	private Restaurant restaurant;
	private Boolean glutenFree;
	private Boolean vegeterian;
	
	public abstract Double getPrice();

}
