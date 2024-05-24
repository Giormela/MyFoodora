package myFoodora.entities.user;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import myFoodora.entities.FidelityCard;
import myFoodora.entities.Order;
import myFoodora.entities.food.Dish;
import myFoodora.entities.food.Food;
import myFoodora.entities.food.Meal;
import myFoodora.enums.DishType;
import myFoodora.enums.MealType;

public class Restaurant extends LocalizedUser {
	private Double genericDiscountFactor;
	private Double specialDiscountFactor;
	private Map<Customer, FidelityCard> fidelityCards;
	private Map<String, Dish> menu;
	private Map<Integer, Meal> meals;
	
	
	public Restaurant() {
		super();
		this.genericDiscountFactor = 5.0;
		this.specialDiscountFactor = 10.0;
		this.fidelityCards = new HashMap<Customer, FidelityCard>();
		this.menu = new HashMap<String, Dish>();
		this.meals = new HashMap<Integer, Meal>();
	}
	
	public Collection<Meal> getSortedMeals(){
		Stream<Meal> allMealsOrdered = orderHistory.stream()
			.flatMap(o->o.getMeals())
			.filter(m->m.getMealType() == MealType.Half_Meal);
		return sortByFrequence(allMealsOrdered);
	}
	
	public Collection<Dish> getSortedDishes(){
		Stream<Dish> allDishesOrdered = orderHistory.stream().flatMap(o->o.getDishes());
		return sortByFrequence(allDishesOrdered);	
	}
	
	private <T extends Food> Collection<T> sortByFrequence(Stream<T> stream){
		return stream.collect(Collectors.toMap(f->f, f->1, (a,b)->a+b))
			.entrySet().stream()
			.sorted(Comparator.comparing(Map.Entry::getValue))
			.map(Map.Entry::getKey)
			.toList();
	}
	
//	public void addDish(Dish newDish) {
//		menu.put(newDish.getName(), newDish);
//	}
//	
//	public void removeDish(String dishName) {
//		menu.remove(dishName);		
//	}
//	
//	public void addMeal(Collection<Dish> dishes, ) {
//		Meal NewMeal = new Meal(dishes);
//		meals.put(newMeal.hashCode(), newMeal);
//	}
//	
//	
//	
//	public void removeMeal(Meal meal) {
//		meals.remove(meal.hashCode());
//	}
	
	private Collection<Dish> getDishesOfType(DishType dishCategory){
		return menu.values().stream().filter(d->d.getCategory()==dishCategory).toList();
	}
	
	public Double getProfit() {
		return orderHistory.stream().mapToDouble(Order::getProfit).sum();
	}
	
	public void addFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCards.putIfAbsent(fidelityCard.getCustomer(), fidelityCard);
	}
	
	public void removeFidelityCard(Customer customer) {
		this.fidelityCards.remove(customer);
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
}
