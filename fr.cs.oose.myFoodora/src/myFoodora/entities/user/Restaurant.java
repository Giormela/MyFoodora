package myFoodora.entities.user;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import myFoodora.entities.Order;
import myFoodora.entities.food.Dish;
import myFoodora.entities.food.Food;
import myFoodora.entities.food.Meal;
import myFoodora.entities.food.MealBuilder;
import myFoodora.enums.MealType;
import myFoodora.exceptions.ElementNotFoundException;
import myFoodora.exceptions.MealCreationException;

/**
 * Represents a restaurant in the system. This class extends LocalizedUser.
 *
 * The Restaurant class manages dishes and meals, including the creation, addition, and removal
 * of dishes from its menu. It also supports the creation and management of meals using a MealBuilder.
 * The class handles meal discounts, orders placed to the restaurant, and tracks meal and dish popularity by order frequency.
 *
 * Attributes:
 * - genericDiscountFactor (Double): The default discount rate applied to meals not designated as Meal of the Week.
 * - specialDiscountFactor (Double): An enhanced discount rate applied to the Meal of the Week.
 * - menu (Map<String, Dish>): A collection of dishes offered by the restaurant, indexed by dish name.
 * - meals (Map<String, Meal>): A collection of fully prepared meals available, indexed by meal name.
 * - unreadyMeals (Map<String, MealBuilder>): Active MealBuilders for meals that are currently being created but are not yet finalized.
 *
 * Methods include functionality to add and remove dishes, create and finalize meals, manage Meal of the Week
 * settings, and display detailed information about the restaurant's offerings and current status.
 */
public class Restaurant extends LocalizedUser {
    private Double genericDiscountFactor;
    private Double specialDiscountFactor;
    //private Map<Customer, FidelityCard> fidelityCards;
    private Map<String, Dish> menu;
    private Map<String, Meal> meals;
    private Map<String, MealBuilder> unreadyMeals;

    public Restaurant() {
        super();
        this.genericDiscountFactor = 5.0;
        this.specialDiscountFactor = 10.0;
        //this.fidelityCards = new HashMap<Customer, FidelityCard>();
        this.menu = new HashMap<String, Dish>();
        this.meals = new HashMap<String, Meal>();
        this.unreadyMeals = new HashMap<String, MealBuilder>();
    }

    public Collection<Meal> getSortedMeals() {
        Stream<Meal> allMealsOrdered = orderHistory.stream()
                .flatMap(Order::getMeals)
                .filter(m -> m.getMealType() == MealType.Half_Meal);
        return sortFoodByFrequence(allMealsOrdered);
    }

    public Collection<Dish> getSortedDishes() {
        Stream<Dish> allDishesOrdered = orderHistory.stream().flatMap(Order::getDishes);
        return sortFoodByFrequence(allDishesOrdered);
    }

    private <T extends Food> Collection<T> sortFoodByFrequence(Stream<T> stream) {
        return stream.collect(Collectors.toMap(f -> f, f -> 1, (a, b) -> a + b))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();
    }

    /**
     * Adds a dish to the restaurant menu.
     * @param newDish The dish to add to the menu.
     */
    public void addDish(Dish newDish) {
        menu.put(newDish.getName(), newDish);
    }

    /**
     * Removes a dish from the restaurant menu.
     * @param dishName The name of the dish to remove.
     */
    public void removeDish(String dishName) {
        menu.remove(dishName);
    }

    /**
     * Retrieves a food item by name from either the menu or the meals.
     * @param foodName The name of the food item to retrieve.
     * @return The food item, either a Dish or a Meal.
     * @throws ElementNotFoundException If the food item is not found in the menu or meals.
     */
    public Food getFood(String foodName) throws ElementNotFoundException{  		
    	if (menu.containsKey(foodName) || meals.containsKey(foodName)) {
    		Dish d = menu.get(foodName);
    		Meal m = meals.get(foodName);
    		return d==null?m:d;
    	}
    	throw new ElementNotFoundException("The restaurant "+getName()+" doesn't contain any item called "+foodName);
    }

    /**
     * Initiates the creation of a meal with a specified name.
     * @param mealName The name of the new meal to start creating.
     * @throws MealCreationException If a meal with the same name is already being created.
     */
    public void createMeal(String mealName) throws MealCreationException {
    	if (unreadyMeals.containsKey(mealName))
    		throw new MealCreationException("The restaurant "+getName()+" was already creating the meal "+mealName);
    	unreadyMeals.put(mealName, new MealBuilder(this, mealName));
    }

    /**
     * Adds a dish to a meal being created.
     * @param mealName The name of the meal to add the dish to.
     * @param dishName The name of the dish to add to the meal.
     * @throws ElementNotFoundException If the dish or meal is not found.
     * @throws MealCreationException If the meal is not being created.
     */
    public void addDishToMeal(String mealName, String dishName) throws ElementNotFoundException, MealCreationException {
    	if (!(menu.containsKey(dishName) && unreadyMeals.containsKey(mealName))) 
    		throw new ElementNotFoundException("The restaurant "+getName()+" didn't find the meal "+mealName+" or the dish "+dishName);
    	
    	unreadyMeals.get(mealName).addDish(menu.get(dishName));
    }

    /**
     * Saves a meal that has been created.
     * @param mealName The name of the meal to save.
     * @throws ElementNotFoundException If the meal is not found.
     * @throws MealCreationException If the meal is not being created.
     */
    public void saveMeal(String mealName) throws ElementNotFoundException, MealCreationException {
    	if (!unreadyMeals.containsKey(mealName))
    		throw new ElementNotFoundException("The restaurant "+getName()+" doesn't contain any meal called "+mealName);
    	
    	
    	Meal newMeal = unreadyMeals.get(mealName).build();
        meals.put(newMeal.getName(), newMeal);
        unreadyMeals.remove(mealName);
    }

    public String displayMeal(String mealName) throws ElementNotFoundException {
    	if (!(unreadyMeals.containsKey(mealName) || meals.containsKey(mealName)))
    		throw new ElementNotFoundException("The restaurant "+getName()+" doesn't contain any meal called "+mealName);
    	
    	return Stream.concat(
    			meals.entrySet().stream(),
    			unreadyMeals.entrySet().stream()
    			)
    		.filter(e->e.getKey().equals(mealName))
    		.map(e->e.getValue().display())
    		.findFirst().get();
    }

    public void setMealOfTheWeek(String mealName) {
    	if (meals.containsKey(mealName)) 
    		meals.get(mealName).setMealOfTheWeek(true);
    }
    
    public void setNotMealOfTheWeek(String mealName) {
    	if (meals.containsKey(mealName)) 
    		meals.get(mealName).setMealOfTheWeek(false);
    }

    public void removeMeal(String mealName) {
        meals.remove(mealName);
    }

    public Double getProfit() {
        return orderHistory.stream().mapToDouble(Order::getProfit).sum();
    }

    public Double getGenericDiscountFactor() {
        return genericDiscountFactor;
    }

    public void setGenericDiscountFactor(Double genericDiscountFactor) {
        this.genericDiscountFactor = genericDiscountFactor;
    }

    public Double getSpecialDiscountFactor() {
        return specialDiscountFactor;
    }

    public void setSpecialDiscountFactor(Double specialDiscountFactor) {
        this.specialDiscountFactor = specialDiscountFactor;
    }

	@Override
	public String display() {
		StringBuilder sb = new StringBuilder(super.display());
		sb.append(String.format(" Name             : %s\n", name));
        //sb.append(String.format(" Location         : %s\n", location));
        sb.append(String.format(" Generic Discount : %s\n", genericDiscountFactor));
        sb.append(String.format(" Special Discount : %s\n", specialDiscountFactor));
        sb.append("-------------------------------------\n");
        sb.append("                Menù                 \n");
        menu.values().stream().forEach(d->sb.append(d.display()));
        sb.append("-------------------------------------\n");
        sb.append("                Meals                \n");
        meals.values().stream().forEach(m->sb.append(m.display()));
        sb.append("=====================================\n");
        return sb.toString();
	}
}
