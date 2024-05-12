package fr.cs.oose.myFoodora.user;

public class Courier extends LocalizedUser {
	private String surname;
	private String phone;
	private Integer count;
	
	Courier() {
		super();
		this.count = 0; 
	}
	
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}