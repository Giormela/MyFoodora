package myFoodora.entities.food;

import myFoodora.entities.user.Restaurant;

abstract public class Food {
	protected Restaurant restaurant;
	protected Boolean glutenFree;
	protected Boolean vegetarian;
	protected Double price;
	protected String name;
	
	public abstract Double getPrice();
}
