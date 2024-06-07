package myFoodora.services;

import myFoodora.entities.Notification;
import myFoodora.entities.user.Customer;

public class CustomerService extends UserService<Customer> {
	
	
	public void sendNotifications(Notification notification) {
		getList().stream().filter(Customer::getConsent).forEach(c->c.notify(notification));
	}
}
