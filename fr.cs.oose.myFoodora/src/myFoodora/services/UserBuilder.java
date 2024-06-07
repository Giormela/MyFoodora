package myFoodora.services;

import myFoodora.entities.Credential;
import myFoodora.entities.Location;
import myFoodora.entities.user.*;
import myFoodora.enums.PermissionType;

/**
 * UserBuilder is an abstract class that provides a blueprint for building User objects.
 * It is used to create User objects with a fluent interface.
 *
 * @param <U> the type of User to build
 */
public abstract class UserBuilder<U extends User> {	
	protected U user;

	/**
	 * Returns a UserBuilder instance for the specified user type.
	 *
	 * @param userType the type of user to build
	 * @return a UserBuilder instance for the specified user type
	 */
	public static <T extends User> UserBuilder<T> buildUserOfType(Class<T> userType) {
		if (Manager.class.isAssignableFrom(userType))
			return (UserBuilder<T>) UserBuilder.ManagerBuilder.getInstance();
		if (Customer.class.isAssignableFrom(userType))
			return (UserBuilder<T>) UserBuilder.CustomerBuilder.getInstance();
		if (Restaurant.class.isAssignableFrom(userType))
			return (UserBuilder<T>) UserBuilder.RestaurantBuilder.getInstance();
		if (Courier.class.isAssignableFrom(userType))
			return (UserBuilder<T>) UserBuilder.CourierBuilder.getInstance();
		else 
			return (UserBuilder<T>) UserBuilder.CustomerBuilder.getInstance();
	}
	
	public UserBuilder<U> addName(String name) {
		this.user.setName(name);
		return this;
	}
	
	protected abstract UserBuilder<U> reset();
	
	public abstract UserBuilder<U> addCredential(String username, String password);
	
	public UserBuilder<U> addLocation(Location location) {
		return this;
	}
	
	public UserBuilder<U> addSurname(String surname){
		return this;
	}
	
	public UserBuilder<U> addEmail(String email){
		return this;
	}
	
	public UserBuilder<U> addPhone(String phone){
		return this;
	}
	
	public UserBuilder<U> addGenericDiscountFactor(Double genericDiscountFactor){
		return this;
	}
	
	public UserBuilder<U> addSpecialDiscountFactor(Double specialDiscountFactor){
		return this;
	}
	
	public UserBuilder<U> addConsent(Boolean consent) {
		return this;
	}
	
	public U getResult() {
		return this.user;
	}

	/**
	 * ManagerBuilder is a concrete subclass of UserBuilder that provides a blueprint for building Manager objects.
	 * It is used to create Manager objects with a fluent interface.
	 */
	static class ManagerBuilder extends UserBuilder<Manager>{
		private static ManagerBuilder managerBuilder;
		
		private static UserBuilder<Manager> getInstance() {
			if (managerBuilder == null) 
				managerBuilder = new ManagerBuilder();
			return managerBuilder.reset(); 
		}
		@Override
		protected UserBuilder<Manager> reset() {
			this.user = new Manager();
			return this;
		}
		@Override
		public UserBuilder<Manager> addCredential(String username, String password) {
			Credential credential = new Credential(username, password, this.user, PermissionType.Manager);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder<Manager> addSurname(String surname) {
			this.user.setSurname(surname);
			return this;
		}
	}

	/**
	 * LocalizedUserBuilder is an abstract subclass of UserBuilder that provides a blueprint for building LocalizedUser objects.
	 * It is used to create LocalizedUser objects with a fluent interface.
	 *
	 * @param <L> the type of LocalizedUser to build
	 */
	public abstract static class LocalizedUserBuilder<L extends LocalizedUser> extends UserBuilder<L>{
		public UserBuilder<L> addLocation(Location location) {
			this.user.setLocation(location);
			return this;
		}
	}

	/**
	 * CustomerBuilder is a concrete subclass of LocalizedUserBuilder that provides a blueprint for building Customer objects.
	 * It is used to create Customer objects with a fluent interface.
	 */
	public static class CustomerBuilder extends LocalizedUserBuilder<Customer> {
		private static CustomerBuilder customerBuilder;

		private static UserBuilder<Customer> getInstance() {
			if (customerBuilder == null) 
				customerBuilder = new CustomerBuilder();
			return customerBuilder.reset();
		}
		@Override
		protected UserBuilder<Customer> reset() {
			this.user = new Customer();
			return this;
		}
		@Override
		public UserBuilder<Customer> addCredential(String username, String password) {
			Credential credential = new Credential(username, password, this.user, PermissionType.Customer);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder<Customer> addSurname(String surname) {
			this.user.setSurname(surname);
			return this;
		}
		@Override
		public UserBuilder<Customer> addEmail(String email) {
			this.user.setEmail(email);
			return this;
		}
		@Override
		public UserBuilder<Customer> addPhone(String phone) {
			this.user.setPhone(phone);
			return this;
		}
		@Override
		public UserBuilder<Customer> addConsent(Boolean consent) {
			this.user.setConsent(consent);
			return this;
		}
	}
	
	
	/**
	 * RestaurantBuilder is a concrete subclass of LocalizedUserBuilder that provides a blueprint for building Restaurant objects.
	 * It is used to create Restaurant objects with a fluent interface.
	 */
	public static class RestaurantBuilder extends LocalizedUserBuilder<Restaurant> {
		private static RestaurantBuilder restaurantBuilder;
		
		private static UserBuilder<Restaurant> getInstance() {
			if (restaurantBuilder == null) 
				restaurantBuilder = new RestaurantBuilder();
			return restaurantBuilder.reset();
		}
		@Override
		protected UserBuilder<Restaurant> reset() {
			this.user = new Restaurant();
			return this;
		}
		@Override
		public UserBuilder<Restaurant> addCredential(String username, String password) {
			Credential credential = new Credential(username, password, this.user, PermissionType.Restaurant);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder<Restaurant> addGenericDiscountFactor(Double genericDiscountFactor) {
			this.user.setGenericDiscountFactor(genericDiscountFactor);
			return this;
		}
		@Override
		public UserBuilder<Restaurant> addSpecialDiscountFactor(Double specialDiscountFactor) {
			this.user.setSpecialDiscountFactor(specialDiscountFactor);
			return this;
		}
	}
		

	/**
	 * CourierBuilder is a concrete subclass of LocalizedUserBuilder that provides a blueprint for building Courier objects.
	 * It is used to create Courier objects with a fluent interface.
	 */
	public static class CourierBuilder extends LocalizedUserBuilder<Courier> {
		private static CourierBuilder courierBuilder;
		
		private static UserBuilder<Courier> getInstance() {
			if (courierBuilder == null) 
				courierBuilder = new CourierBuilder();
			return courierBuilder.reset();
		}
		@Override
		protected UserBuilder<Courier> reset() {
			this.user = new Courier();
			return this;
		}
		@Override
		public UserBuilder<Courier> addCredential(String username, String password) {
			Credential credential = new Credential(username, password, this.user, PermissionType.Courier);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder<Courier> addSurname(String surname) {
			this.user.setSurname(surname);
			return this;
		}
		@Override
		public UserBuilder<Courier> addPhone(String phone) {
			this.user.setPhone(phone);
			return this;
		}
	}
}
