package fr.cs.oose.myFoodora.user;

abstract class LocalizedUser extends User {
	private Location location;

	LocalizedUser(Integer id, String name, Credential credential, Location location) {
		super(id, name, credential);
		this.location = location;
	}

	LocalizedUser() {
		super();
	}
	
	
	
}
