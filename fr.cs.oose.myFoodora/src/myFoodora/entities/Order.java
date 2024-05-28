package myFoodora.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Stream;

import myFoodora.entities.fidelityCard.FidelityCard;
import myFoodora.entities.food.Dish;
import myFoodora.entities.food.Food;
import myFoodora.entities.food.Meal;
import myFoodora.entities.user.Courier;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.OrderState;

public class Order {
	private String name;
	private Customer customer;
	private Restaurant restaurant;
	private Collection<Food> food;
	private Courier courier;
	private Date date;
	private OrderState state;
	private Double pricePayed;
	private Double generatedProfit;
	
	
	public Order(Customer customer, Restaurant restaurant, String name) {
		super();
		this.name = name;
		this.customer = customer;
		this.restaurant = restaurant;
		this.food = new ArrayList<Food>();
		this.date = Date.now();
		this.state = OrderState.Pending;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		return generatedProfit;
	}
	public void setProfit(Double profit) {
		this.generatedProfit = profit;
	}
	public Date getData() {
		return date;
	}
	public Double getPricePayed() {
		return pricePayed;
	}
	public void addFood(Food f) {
		food.add(f);
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
	public void pay(FidelityCard fidelityCard, Date date) {
		this.date = date;
		Double fullPrice = food.stream().mapToDouble(Food::getPrice).sum();
		Double discountedPrice = fidelityCard.apply(fullPrice);
		pricePayed = discountedPrice;
	}	
}
