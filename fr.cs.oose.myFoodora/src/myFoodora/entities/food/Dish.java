package myFoodora.entities.food;

import myFoodora.entities.user.Restaurant;
import myFoodora.enums.DishType;
import myFoodora.enums.FoodCategory;

public class Dish extends Food{
    private DishType dishType;
    private Double price;

    public Dish(Restaurant restaurant, String name, DishType dishType, FoodCategory foodCategory, Double price) {
    	this.restaurant = restaurant;
    	this.name = name;
    	this.dishType = dishType;
    	this.foodCategory = foodCategory;
    	this.price = price;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    public DishType getDishType() {
        return dishType;
    }

    public void setDishType(DishType dishType) {
        this.dishType = dishType;
    }
}
