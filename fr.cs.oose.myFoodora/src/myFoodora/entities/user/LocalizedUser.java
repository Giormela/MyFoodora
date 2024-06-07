package myFoodora.entities.user;

import java.util.HashSet;
import java.util.Set;

import myFoodora.entities.Location;
import myFoodora.entities.Order;

/**
 * Represents a user with a location
 */
public abstract class LocalizedUser extends User {
	protected Location location;
	protected Set<Order> orderHistory;

	public LocalizedUser() {
		super();
		this.orderHistory = new HashSet<Order>();
	}

	public void addOrderToHistory(Order order) {
		orderHistory.add(order);
	}

	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public Double getDistanceFrom(LocalizedUser other) {
		return this.location.getDistanceFrom(other.location);
	}
}
