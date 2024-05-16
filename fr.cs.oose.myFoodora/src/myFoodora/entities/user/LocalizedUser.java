package myFoodora.entities.user;

import myFoodora.entities.Location;

public abstract class LocalizedUser extends User {
	private Location location;
	private Boolean activate;

	public LocalizedUser() {
		super();
		this.activate = true;
	}
	
	public Boolean getActivate() {
		return activate;
	}


	public void setActivate(Boolean activate) {
		this.activate = activate;
	}


	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	
}
