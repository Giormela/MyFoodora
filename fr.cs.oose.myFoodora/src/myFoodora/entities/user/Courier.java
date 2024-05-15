package myFoodora.entities.user;

import myFoodora.enums.CourierState;

public class Courier extends LocalizedUser {
	private String surname;
	private String phone;
	private Integer count;
	private CourierState state;
	
	public Courier() {
		super();
		this.count = 0; 
		this.state = CourierState.OffDuty;
	}
	
	public void setState(CourierState state) {
		this.state = state;
	}

	public Integer getCount() {
		return count;
	}

	public CourierState getState() {
		return state;
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
