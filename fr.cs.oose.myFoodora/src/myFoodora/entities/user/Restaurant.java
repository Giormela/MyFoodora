package myFoodora.entities.user;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import myFoodora.entities.FidelityCard;
import myFoodora.entities.Order;

public class Restaurant extends LocalizedUser {
	private Double genericDiscountFactor;
	private Double specialDiscountFactor;
	private Set<Order> orderHistory;
	private Set<FidelityCard> fidelityCards;
//	private Map<String, Dish> menu;
//	private Map<String, Meal> meals;
	
	
	Restaurant() {
		super();
		this.genericDiscountFactor = 5.0;
		this.specialDiscountFactor = 10.0;
		this.fidelityCards = new HashSet<FidelityCard>();
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
	
	public void addOrder(Order order) {
		this.orderHistory.add(order);
	}
	
	public void addFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCards.add(fidelityCard);
	}
	
	public void removeFidelityCard(Customer customer) {
		Optional<FidelityCard> optional = this.fidelityCards.stream().filter(fc -> fc.getCustomer().equals(customer)).findAny();
		if (optional.isPresent()) {
			this.fidelityCards.remove(optional.get());
		}
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
