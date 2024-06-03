package myFoodora.entities.food;

import myFoodora.clui.Display;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.FoodCategory;

abstract public class Food implements Display{
	protected Restaurant restaurant;
	protected FoodCategory foodCategory;
	protected String name;
	
	public abstract Double getPrice();
	
	
	public FoodCategory getFoodCategory() {
		return foodCategory;
	}
	public void setFoodCategory(FoodCategory foodCategory) {
		this.foodCategory = foodCategory;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
    public String getName() {
        return name;
    }
    @Override
    public String display() {
    	String title = " "+name;
    	String padding = " ".repeat(33 - title.length());
    	return title+padding+getPrice()+"\n";
	}
}
