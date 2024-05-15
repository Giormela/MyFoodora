package myFoodora.entities.user;

import myFoodora.entities.Location;

public abstract class LocalizedUser extends User {
	private Location location;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	
}
