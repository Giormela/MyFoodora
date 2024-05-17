package myFoodora.services;

import myFoodora.entities.Credential;
import myFoodora.entities.Location;
import myFoodora.entities.user.*;
import myFoodora.enums.PermissionType;

public abstract class UserBuilder<U extends User> {	
	protected U user;
	
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
	
	public abstract UserBuilder<U> reset();
	
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
	
	public U getResult() {
		return this.user;
	}
	
	static class ManagerBuilder extends UserBuilder<Manager>{
		private static ManagerBuilder managerBuilder;
		
		private static UserBuilder<Manager> getInstance() {
			if (managerBuilder == null) 
				managerBuilder = new ManagerBuilder();
			return managerBuilder.reset(); 
		}
		@Override
		public UserBuilder<Manager> reset() {
			this.user = new Manager();
			return this;
		}
		@Override
		public UserBuilder<Manager> addCredential(String username, String password) {
			Credential credential = new Credential(username, password, this.user.getId(), PermissionType.Manager);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder<Manager> addSurname(String surname) {
			((Manager) this.user).setSurname(surname);
			return this;
		}
	}
	
	public abstract static class LocalizedUserBuilder<L extends LocalizedUser> extends UserBuilder<L>{
		public UserBuilder<L> addLocation(Location location) {
			((LocalizedUser) this.user).setLocation(location);
			return this;
		}
	}
	
	public static class CustomerBuilder extends LocalizedUserBuilder<Customer> {
		private static CustomerBuilder customerBuilder;

		private static UserBuilder<Customer> getInstance() {
			if (customerBuilder == null) 
				customerBuilder = new CustomerBuilder();
			return customerBuilder.reset();
		}
		@Override
		public UserBuilder<Customer> reset() {
			this.user = new Customer();
			return this;
		}
		@Override
		public UserBuilder<Customer> addCredential(String username, String password) {
			Credential credential = new Credential(username, password, this.user.getId(), PermissionType.Customer);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder<Customer> addSurname(String surname) {
			((Customer) this.user).setSurname(surname);
			return this;
		}
		@Override
		public UserBuilder<Customer> addEmail(String email) {
			((Customer) this.user).setEmail(email);
			return this;
		}
		@Override
		public UserBuilder<Customer> addPhone(String phone) {
			((Customer) this.user).setPhone(phone);
			return this;
		}
	}
	
	
	
	public static class RestaurantBuilder extends LocalizedUserBuilder<Restaurant> {
		private static RestaurantBuilder restaurantBuilder;
		
		private static UserBuilder<Restaurant> getInstance() {
			if (restaurantBuilder == null) 
				restaurantBuilder = new RestaurantBuilder();
			return restaurantBuilder.reset();
		}
		@Override
		public UserBuilder<Restaurant> reset() {
			this.user = new Restaurant();
			return this;
		}
		@Override
		public UserBuilder<Restaurant> addCredential(String username, String password) {
			Credential credential = new Credential(username, password, this.user.getId(), PermissionType.Restaurant);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder<Restaurant> addGenericDiscountFactor(Double genericDiscountFactor) {
			((Restaurant) this.user).setGenericDiscountFactor(genericDiscountFactor);
			return this;
		}
		@Override
		public UserBuilder<Restaurant> addSpecialDiscountFactor(Double specialDiscountFactor) {
			((Restaurant) this.user).setSpecialDiscountFactor(specialDiscountFactor);
			return this;
		}
	}
		

	
	public static class CourierBuilder extends LocalizedUserBuilder<Courier> {
		private static CourierBuilder courierBuilder;
		
		private static UserBuilder<Courier> getInstance() {
			if (courierBuilder == null) 
				courierBuilder = new CourierBuilder();
			return courierBuilder.reset();
		}
		@Override
		public UserBuilder<Courier> reset() {
			this.user = new Courier();
			return this;
		}
		@Override
		public UserBuilder<Courier> addCredential(String username, String password) {
			Credential credential = new Credential(username, password, this.user.getId(), PermissionType.Courier);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder<Courier> addSurname(String surname) {
			((Courier) this.user).setSurname(surname);
			return this;
		}
		@Override
		public UserBuilder<Courier> addPhone(String phone) {
			((Courier) this.user).setPhone(phone);
			return this;
		}
	}
}
