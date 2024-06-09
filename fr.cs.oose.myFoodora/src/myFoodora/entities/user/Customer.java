package myFoodora.entities.user;

import myFoodora.entities.Date;
import myFoodora.entities.Order;
import myFoodora.entities.fidelityCard.BasicFidelityCard;
import myFoodora.entities.fidelityCard.FidelityCard;
import myFoodora.entities.food.Food;
import myFoodora.exceptions.ElementNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Represents a customer in the system. A customer is a user that can order food
 * from restaurants.
 */
public class Customer extends LocalizedUser{
	private String surname;
	private String email;
	private String phone;
	private Boolean consent;
	private FidelityCard fidelityCard;
	private Map<String, Order> cart;
	
	
	public Customer() {
		super();
		this.consent = true;
		this.orderHistory = new HashSet<Order>();
		this.fidelityCard = new BasicFidelityCard();
		this.cart = new HashMap<String, Order>();
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean getConsent() {
		return consent;
	}
	public void setConsent(Boolean consensus) {
		this.consent = consensus;
	}
	public void setFidelityCard(FidelityCard fidelityCard) {
		this.fidelityCard = fidelityCard;
	}

	/**
	 * prepares a new order
	 * @param newOrder the order to prepare
	 */
	public void prepareNewOrder(Order newOrder) {
		cart.put(newOrder.getName(), newOrder);
	}
	public Map<String, Order> getCart() {
		return cart;
	}

	/**
	 * Adds a food to an order in the cart.
	 * @param orderName the name of the order to add the food to
	 * @param foodName the name of the food to add
	 * @throws ElementNotFoundException if the order does not exist in the cart
	 */
	public void addFoodToOrder(String orderName, String foodName) throws ElementNotFoundException{
		if (!cart.containsKey(orderName)) {
			throw new ElementNotFoundException("There is no order named "+orderName+" in your cart");
		}
		Order order = cart.get(orderName);
		Food food = order.getRestaurant().getFood(foodName);
		order.addFood(food);
	}

	/**
	 * Pays for an order in the cart.
	 * @param orderName the name of the order to pay
	 * @param date the date of the payment
	 * @return the paid order
	 * @throws ElementNotFoundException if the order does not exist in the cart
	 */
	public Order payOrder(String orderName, Date date) throws ElementNotFoundException{
		if (!cart.containsKey(orderName)) {
			throw new ElementNotFoundException("There is no order named "+orderName+" in your cart");
		}
		Order order = cart.remove(orderName);
		order.pay(fidelityCard, date);
		return order;
	}
	
	@Override
	public String display() {
		StringBuilder sb = new StringBuilder(super.display());
		sb.append(String.format(" Name       : %s %s\n", name, surname));
        sb.append(String.format(" Location   : %s\n", location));
        sb.append(String.format(" Email      : %s\n", email));
        sb.append(String.format(" Phone      : %s\n", phone));
        sb.append(String.format(" Newsletter : %s\n", consent));
        sb.append("=====================================\n");
        return sb.toString();
	}
}
