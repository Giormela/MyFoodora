package fr.cs.oose.myFoodora.user;

abstract class LocalizedUser extends User {
	private Location location;

	LocalizedUser(Integer id, String name, Credential credential, Location location) {
		super();
		this.location = location;
	}

	LocalizedUser() {
		super();
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
	
	
}
