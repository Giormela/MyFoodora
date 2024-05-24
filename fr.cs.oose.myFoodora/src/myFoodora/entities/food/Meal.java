package myFoodora.entities.food;

import java.util.Collection;

import myFoodora.entities.user.Restaurant;
import myFoodora.enums.MealType;
import myFoodora.exceptions.MealCreationException;


public class Meal extends Food {
    //private MealCategory mealCategory; // Standard, Vegetarian, GlutenFree, VegetarianGlutenFree
    private MealType mealType; // Half_Meal, Full_Meal
    private Collection<Dish> dishes;
    private Boolean mealOfTheWeek;

    public Meal(Collection<Dish> dishes, String name, Restaurant restaurant) throws MealCreationException {//throws Exception{
        if (dishes.size() != 2 && dishes.size() != 3) {
            throw new MealCreationException("A meal must have 2 or 3 dishes");
        }
        if (dishes.size() == 2) {
            this.mealType = MealType.Half_Meal;
        }
        if (dishes.size() == 3) {
            this.mealType = MealType.Full_Meal;
        }
        this.dishes = dishes;
        this.restaurant = restaurant;
        this.vegetarian = dishes.stream().allMatch(Dish::isVegetarian);
        this.glutenFree = dishes.stream().allMatch(Dish::isGlutenFree);
        this.mealOfTheWeek = false;
        this.name = name;
    }

    @Override
    public Double getPrice() {
        if (mealOfTheWeek) {
            return this.dishes.stream().mapToDouble(Dish::getPrice).sum() * (1 - (this.restaurant.getSpecialDiscountFactor() / 100));
        } else {
            return this.dishes.stream().mapToDouble(Dish::getPrice).sum() * (1 - (this.restaurant.getGenericDiscountFactor() / 100));
        }
    }

    public Boolean isVegeterianAndGlutenFree() {
        return vegetarian && glutenFree;
    }

    public MealType getMealType() {
        return mealType;
    }

    public Boolean getMealOfTheWeek() {
        return mealOfTheWeek;
    }

    public void setMealOfTheWeek(Boolean mealOfTheWeek) {
        this.mealOfTheWeek = mealOfTheWeek;
    }

    public String getName() {
        return name;
    }
}
