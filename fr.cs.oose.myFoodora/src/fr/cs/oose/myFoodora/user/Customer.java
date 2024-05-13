package fr.cs.oose.myFoodora.user;

public class Customer extends LocalizedUser {
	private String surname;
	private String email;
	private String phone;
	private Boolean consensus;
	
	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public Boolean getConsensus() {
		return consensus;
	}


	public void setConsensus(Boolean consensus) {
		this.consensus = consensus;
	}
	
	
	
	
}
