package myFoodora.entities.user;

import myFoodora.enums.CourierState;

public class Courier extends LocalizedUser {
	private String surname;
	private String phone;
	private CourierState state;
	
	public Courier() {
		super();
		this.state = CourierState.OffDuty;
	}
	
	public void setState(CourierState state) {
		this.state = state;
	}

	public Integer getOrderCount() {
		return orderHistory.size();
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
	public boolean isOffDuty() {
		return this.state == CourierState.OffDuty;
	}
	
	@Override
	public String display() {
		StringBuilder sb = new StringBuilder(super.display());
		sb.append(String.format(" Name       : %s %s\n", name, surname));
        sb.append(String.format(" Location   : %s\n", location));
        sb.append(String.format(" Phone      : %s\n", phone));
        sb.append("=====================================\n");
        return sb.toString();
	}
	
}
