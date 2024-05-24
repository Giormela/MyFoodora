package myFoodora.entities;

import java.util.Collection;
import java.util.stream.Stream;

import myFoodora.entities.food.Dish;
import myFoodora.entities.food.Food;
import myFoodora.entities.food.Meal;
import myFoodora.entities.user.Courier;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.OrderState;

public class Order {
	private Customer customer;
	private Restaurant restaurant;
	private Collection<Food> food;
	private Courier courier;
	private Date data;
	private OrderState state;
	private Double profit;
	
	
	public Order(Customer customer, Restaurant restaurant, Collection<Food> food) {
		super();
		this.customer = customer;
		this.restaurant = restaurant;
		this.food = food;
		this.data = Date.now();
		this.state = OrderState.Pending;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}
	public Courier getCourier() {
		return courier;
	}
	public void setCourier(Courier courier) {
		this.courier = courier;
	}
	public OrderState getState() {
		return state;
	}
	public void setState(OrderState state) {
		this.state = state;
	}
	public Double getProfit() {
		return profit;
	}
	public void setProfit(Double profit) {
		this.profit = profit;
	}
	public Date getData() {
		return data;
	}
	public Stream<Meal> getMeals(){
		return food.stream().filter(f->f instanceof Meal).map(f->(Meal)f);
	}
	public Stream<Dish> getDishes(){
		return food.stream().filter(f->f instanceof Dish).map(f->(Dish)f);
	}
	public Collection<Food> getFood() {
		return food;
	}
	public Double getPrice() {
		Double fullPrice = food.stream().mapToDouble(Food::getPrice).sum();
		Double discountedPrice = customer.applyFidelityCard(restaurant, fullPrice);
		return discountedPrice;
	}
	
}
