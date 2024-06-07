package myFoodora.entities.food;

import java.util.ArrayList;
import java.util.Collection;

import myFoodora.entities.user.Restaurant;
import myFoodora.enums.MealType;

/**
 * The class Meal represents a meal in the system. A meal is a set of dishes
 * that can be ordered together. A meal can be of a specific type (starter,
 * main, dessert) and can be the meal of the week.
 *
 * @see Food
 * @see Dish
 * @see MealType
 * @see Restaurant
 */
public class Meal extends Food {
    private MealType mealType; 
    private Collection<Dish> dishes;
	private Boolean mealOfTheWeek;
    
    public Meal(Restaurant restaurant, String name) {
    	this.restaurant = restaurant;
    	this.name = name;
    	this.dishes = new ArrayList<Dish>();
    	this.mealOfTheWeek = false;
    }

    @Override
    public Double getPrice() {
        if (mealOfTheWeek) {
            return this.dishes.stream().mapToDouble(Dish::getPrice).sum() * (1 - (this.restaurant.getSpecialDiscountFactor() / 100));
        } else {
            return this.dishes.stream().mapToDouble(Dish::getPrice).sum() * (1 - (this.restaurant.getGenericDiscountFactor() / 100));
        }
    }

    public void addDish(Dish dish) {
    	dishes.add(dish);
    }
    public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}
    public MealType getMealType() {
        return mealType;
    }
    public Boolean isMealOfTheWeek() {
        return mealOfTheWeek;
    }
    public void setMealOfTheWeek(Boolean mealOfTheWeek) {
        this.mealOfTheWeek = mealOfTheWeek;
    }
    public Collection<Dish> getDishes() {
		return dishes;
	}
    
    @Override
	public String display() {
    	StringBuilder sb = new StringBuilder(" "+name+"\n");
    	dishes.stream().forEach(d->sb.append("    "+d.getName()+" ".repeat(29-d.getName().length())+d.getPrice()+"\n"));
    	sb.append(" ".repeat(29)+"- "+(isMealOfTheWeek()?restaurant.getSpecialDiscountFactor():restaurant.getGenericDiscountFactor())+" %\n");
    	sb.append(" ".repeat(31)+getPrice()+"\n");
		return sb.toString();
	}
}
