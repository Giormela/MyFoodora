package myFoodora.entities.food;

import java.util.Collection;

import myFoodora.clui.Display;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.FoodCategory;
import myFoodora.enums.MealType;
import myFoodora.exceptions.MealCreationException;

public class MealBuilder implements Display{
	private Meal meal;

	public MealBuilder(Restaurant restaurant, String name) {
		this.meal = new Meal(restaurant, name);
	}

	public void addDish(Dish dish) throws MealCreationException{
		if (!meal.getRestaurant().equals(dish.getRestaurant()))
			throw new MealCreationException("Meal and Dish are not part of the same restaurant");
		if (meal.getDishes().contains(dish))
			throw new MealCreationException("Dish already present in the meal");
		if (meal.getDishes().stream().map(Dish::getDishType).anyMatch(dt->dt.equals(dish.getDishType())))
			throw new MealCreationException(dish.getDishType() + " already present in the meal");
		meal.addDish(dish);
	}
	
	public Meal build() throws MealCreationException {
		Collection<Dish> dishes = meal.getDishes();
		switch (dishes.size()) {
		case 2:
			meal.setMealType(MealType.Half_Meal);
			break;
		case 3:
			meal.setMealType(MealType.Full_Meal);
			break;
		default:
			throw new MealCreationException("Number of dishes incompatible");
		}
		
		if (dishes.stream().allMatch(d->d.getFoodCategory().equals(FoodCategory.Vegeterian)))
			meal.setFoodCategory(FoodCategory.Vegeterian);
		else if (dishes.stream().allMatch(d->d.getFoodCategory().equals(FoodCategory.GlutenFree)))
			meal.setFoodCategory(FoodCategory.GlutenFree);
		else {
			meal.setFoodCategory(FoodCategory.Standard);
		}
		
		return meal;
	}

	@Override
	public String display() {
		return meal.display();
	}
	
	
}
