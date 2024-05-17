package myFoodora.entities.user;

import myFoodora.entities.Location;

public abstract class LocalizedUser extends User {
	private Location location;
	private Boolean active;

	public LocalizedUser() {
		super();
		this.active = true;
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
	
	
	
}
