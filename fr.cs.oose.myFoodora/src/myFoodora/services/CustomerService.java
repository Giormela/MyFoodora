package myFoodora.services;

import myFoodora.entities.Notification;
import myFoodora.entities.user.Customer;

/**CustomerService is a class extending UserService. It is a service class to manage the customers of the system.
 * It is a singleton class.
 *
 * @see UserService
 */
public class CustomerService extends UserService<Customer> {
	
	/**Unique instance of CustomerService*/
	public void sendNotifications(Notification notification) {
		getList().stream().filter(Customer::getConsent).forEach(c->c.notify(notification));
	}
}
