package myFoodora.entities.user;

abstract class LocalizedUser extends User {
	private Location location;

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	
}