package myFoodora.entities.user;

import java.util.HashMap;
import java.util.Map;

import myFoodora.entities.FidelityCard;
import myFoodora.entities.Order;

public class Restaurant extends LocalizedUser {
	private Double genericDiscountFactor;
	private Double specialDiscountFactor;
	private Map<Customer, FidelityCard> fidelityCards;
//	private Map<String, Dish> menu;
//	private Map<String, Meal> meals;
	
	
	public Restaurant() {
		super();
		this.genericDiscountFactor = 5.0;
		this.specialDiscountFactor = 10.0;
		this.fidelityCards = new HashMap<Customer, FidelityCard>();
//		this.menu = new HashMap<Dish>();
//		this.meals = new HashMap<Meal>();
	}
	
//	public void addDish(Dish newDish) {
//		this.menu.insert(newDish.getName(), newDish);
//	}
	
//	public void removeDish(String name) {
//		if (menu.contains(name))
//			menu.remove(name);
//	}
	
//	public void addMeal(String mealName, Collection<Dish> dishes,  ) {
//		Meal newMeal = new Meal(mealName, dishes, )
//		this.meals.add(newMeal);
//	}
	
//	public void removeDish(String name) {
//		if (meals.contains(name))
//			meals.remove(name);
//	}
	
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
