package myFoodora.entities.food;

import myFoodora.clui.Display;
import myFoodora.entities.user.Restaurant;

abstract public class Food implements Display{
	protected Restaurant restaurant;
	protected Boolean glutenFree;
	protected Boolean vegetarian;
	//protected Double price;
	protected String name;
	
	public abstract Double getPrice();
	
	public boolean isVegetarian() {
        return this.vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }
    
    @Override
    public String display() {
    	String title = " "+name+" "+(isVegetarian()?"veg ":"")+(isGlutenFree()?"0gl ":"");
    	String padding = " ".repeat(33 - title.length());
    	return title+padding+getPrice()+"\n";
	}
}
