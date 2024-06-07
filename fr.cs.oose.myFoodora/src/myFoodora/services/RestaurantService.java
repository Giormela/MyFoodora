package myFoodora.services;

import java.util.Comparator;
import java.util.Optional;

import myFoodora.entities.user.Restaurant;


public class RestaurantService extends UserService<Restaurant> {
	public Optional<Restaurant> getBestRestaurant() {
		return getList().stream()
			.sorted(Comparator.comparing(Restaurant::getProfit))
			.findFirst();
	}
	
	public Optional<Restaurant> getWorstRestaurant() {
		return getList().stream()
			.sorted(Comparator.comparing(Restaurant::getProfit).reversed())
			.findFirst();
	}
}
