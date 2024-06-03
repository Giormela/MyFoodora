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

    public void addDish(Dish newDish) {
        menu.put(newDish.getName(), newDish);
    }

    public void removeDish(String dishName) {
        menu.remove(dishName);
    }
    
    public Food getFood(String foodName) throws ElementNotFoundException{  		
    	if (menu.containsKey(foodName) || meals.containsKey(foodName)) {
    		Dish d = menu.get(foodName);
    		Meal m = meals.get(foodName);
    		return d==null?m:d;
    	}
    	throw new ElementNotFoundException("The restaurant "+getName()+" doesn't contain any item called "+foodName);
    }
    
    public void createMeal(String mealName) throws MealCreationException {
    	if (unreadyMeals.containsKey(mealName))
    		throw new MealCreationException("The restaurant "+getName()+" was already creating the meal "+mealName);
    	unreadyMeals.put(mealName, new MealBuilder(this, mealName));
    }
    
    public void addDishToMeal(String mealName, String dishName) throws ElementNotFoundException, MealCreationException {
    	if (!(menu.containsKey(dishName) && unreadyMeals.containsKey(mealName))) 
    		throw new ElementNotFoundException("The restaurant "+getName()+" didn't find the meal "+mealName+" or the dish "+dishName);
    	
    	unreadyMeals.get(mealName).addDish(menu.get(dishName));
    }

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
