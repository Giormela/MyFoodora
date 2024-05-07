package fr.cs.oose.myFoodora;

class Customer extends LocalizedUser {
	private String surname;
	private String email;
	private String phone;
	private Boolean consensus;
	
	Customer(Integer id, String name, Credential credential, Location location, String surname, String email,
			String phone, Boolean consensus) {
		super(id, name, credential, location);
		this.surname = surname;
		this.email = email;
		this.phone = phone;
		this.consensus = consensus;
	}
	
	public class ciao extends ciaooo{
		  
	}
	
	
}
