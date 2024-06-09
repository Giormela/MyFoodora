package myFoodora.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Comparator;

import org.junit.jupiter.api.Test;

import myFoodora.entities.user.Restaurant;


public class RestaurantService extends UserService<Restaurant> {
	
	
	public Collection<Restaurant> getTopRestaurant() {
		return getList().stream()
			.sorted(Comparator.comparing(Restaurant::getProfit).reversed())
			.toList();
	}
}
