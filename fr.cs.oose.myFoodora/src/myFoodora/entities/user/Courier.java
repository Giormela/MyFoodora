package myFoodora.entities.user;

import java.util.Optional;

import myFoodora.entities.Notification;
import myFoodora.entities.Order;
import myFoodora.enums.CourierState;


public class Courier extends LocalizedUser {
	private String surname;
	private String phone;
	private CourierState state;
	private Optional<Order> assignedOrder;

	/**
	 * Default constructor initializing the courier with default state as OffDuty
	 * and no assigned orders.
	 */
	public Courier() {
		super();
		this.state = CourierState.OffDuty;
		this.assignedOrder = Optional.empty();
	}

	/**
	 * Assigns an order to this courier and updates the courier's state and notifications.
	 * @param order The order to be assigned to the courier.
	 */
	public void assignOrder(Order order) {
		assignedOrder = Optional.of(order);
		addNotificationTo(new Notification("You have been assigned to a new order!"));;
		setState(CourierState.OnDuty);
	}
	public Optional getAssignedOrder() {
		return assignedOrder;
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
