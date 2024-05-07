package fr.cs.oose.myFoodora;

abstract class User {
	private Integer id;
	private String name;
	private Credential credential;
	
	User(Integer id, String name, Credential credential) {
		super();
		this.id = id;
		this.name = name;
		this.credential = credential;
	}
	
	
	
}
