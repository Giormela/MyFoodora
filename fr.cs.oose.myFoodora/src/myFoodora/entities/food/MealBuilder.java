package myFoodora.entities.food;

import java.util.Collection;

import myFoodora.clui.Display;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.FoodCategory;
import myFoodora.enums.MealType;
import myFoodora.exceptions.MealCreationException;

/**
 * The class MealBuilder is a builder for the Meal class. It allows to create a
 * meal by adding dishes to it. The meal can be of type Half_Meal or Full_Meal
 * and can be of a specific food category (Vegeterian, GlutenFree, Standard).
 *
 * @see Meal
 * @see Dish
 * @see Restaurant
 * @see FoodCategory
 * @see MealType
 */
public class MealBuilder implements Display {
    private Meal meal;

    public MealBuilder(Restaurant restaurant, String name) {
        this.meal = new Meal(restaurant, name);
    }

    /**
     * Add a dish to the meal. The dish must be part of the same restaurant as the
     * meal, must not already be present in the meal, and must not have the same
     * dish type as another dish in the meal.
     *
     * @param dish the dish to add to the meal
     * @throws MealCreationException if the dish is not part of the same restaurant as the meal, if
     *                               the dish is already present in the meal, or if the dish type is
     *                               already present in the meal
     */
    public void addDish(Dish dish) throws MealCreationException {
        if (!meal.getRestaurant().equals(dish.getRestaurant()))
            throw new MealCreationException("Meal and Dish are not part of the same restaurant");
        if (meal.getDishes().contains(dish))
            throw new MealCreationException("Dish already present in the meal");
        if (meal.getDishes().stream().map(Dish::getDishType).anyMatch(dt -> dt.equals(dish.getDishType())))
            throw new MealCreationException(dish.getDishType() + " already present in the meal");
        meal.addDish(dish);
    }

    /**
     * Build the meal and set its meal type and food category.
     *
     * @return the meal
     * @throws MealCreationException if the number of dishes is not 2 or 3
     * @see MealType
     * @see FoodCategory
     */
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

        /* Set the food category of the meal */
        if (dishes.stream().allMatch(d -> d.getFoodCategory().equals(FoodCategory.Vegeterian)))
            meal.setFoodCategory(FoodCategory.Vegeterian);
        else if (dishes.stream().allMatch(d -> d.getFoodCategory().equals(FoodCategory.GlutenFree)))
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
