package fr.cs.oose.myFoodora.user;

public abstract class User {
	private Integer id;
	private String name;
	private Credential credential;
	
	User(Integer id, String name, Credential credential) {
		super();
		this.id = id;
		this.name = name;
		this.credential = credential;
	}
	
	User() {
		super();
	}
	
	
	
}
