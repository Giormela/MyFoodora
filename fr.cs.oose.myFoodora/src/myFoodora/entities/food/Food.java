package myFoodora.entities.food;

import myFoodora.entities.user.Restaurant;

abstract public class Food {
	protected Restaurant restaurant;
	protected Boolean glutenFree;
	protected Boolean vegeterian;
	
	public abstract Double getPrice();

}
