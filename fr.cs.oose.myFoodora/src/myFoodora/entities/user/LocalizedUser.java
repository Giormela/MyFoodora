package myFoodora.entities.user;

import java.util.HashSet;
import java.util.Set;

import myFoodora.entities.Location;
import myFoodora.entities.Order;

public abstract class LocalizedUser extends User {
	protected Location location;
	protected Boolean active;
	protected Set<Order> orderHistory;

	public LocalizedUser() {
		super();
		this.active = true;
		this.orderHistory = new HashSet<Order>();
	}
	
	public void addOrder(Order order) {
		orderHistory.add(order);
	}
	
	public Boolean isActive() {
		return active;
	}


	public void setAvtive(Boolean activate) {
		this.active = activate;
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
