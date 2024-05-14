package myFoodora.entities.user;

import myFoodora.entities.Location;

public abstract class UserBuilder {	
	
	protected User user;
	
	public static UserBuilder buildUserOfType(UserType userType) {
		switch (userType) {
		case Manager: {
			return UserBuilder.ManagerBuilder.getInstance();
		}
		case Courier:{
			return UserBuilder.CourierBuilder.getInstance();
		}
		case Customer:{
			return UserBuilder.CustomerBuilder.getInstance();
		}
		case Restaurant:{
			return UserBuilder.RestaurantBuilder.getInstance();
		}
		default:
			return buildUserOfType(UserType.Customer);
		}
	}
	
	public UserBuilder addName(String name) {
		this.user.setName(name);
		return this;
	}
	
	public abstract UserBuilder reset();
	
	public abstract UserBuilder addCredential(String username, String password);
	
	public UserBuilder addLocation(Location location) {
		return this;
	}
	
	public UserBuilder addSurname(String surname){
		return this;
	}
	
	public UserBuilder addEmail(String email){
		return this;
	}
	
	public UserBuilder addPhone(String phone){
		return this;
	}
	
	public UserBuilder addGenericDiscountFactor(Double genericDiscountFactor){
		return this;
	}
	
	public UserBuilder addSpecialDiscountFactor(Double specialDiscountFactor){
		return this;
	}
	
	public User getResult() {
		return this.user;
	}
	
	static class ManagerBuilder extends UserBuilder{
		private static ManagerBuilder managerBuilder;
		
		private static UserBuilder getInstance() {
			if (managerBuilder == null) 
				managerBuilder = new ManagerBuilder();
			return managerBuilder.reset(); 
		}
		@Override
		public UserBuilder reset() {
			this.user = new Manager();
			return this;
		}
		@Override
		public  UserBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Manager);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder addSurname(String surname) {
			((Manager) this.user).setSurname(surname);
			return this;
		}
	}
	
	public abstract static class LocalizedUserBuilder extends UserBuilder{
		public LocalizedUserBuilder addLocation(Location location) {
			((LocalizedUser) this.user).setLocation(location);
			return this;
		}
	}
	
	public static class CustomerBuilder extends LocalizedUserBuilder {
		private static CustomerBuilder customerBuilder;

		private static UserBuilder getInstance() {
			if (customerBuilder == null) 
				customerBuilder = new CustomerBuilder();
			return customerBuilder.reset();
		}
		@Override
		public UserBuilder reset() {
			this.user = new Customer();
			return this;
		}
		@Override
		public UserBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Customer);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public CustomerBuilder addSurname(String surname) {
			((Customer) this.user).setSurname(surname);
			return this;
		}
		@Override
		public CustomerBuilder addEmail(String email) {
			((Customer) this.user).setEmail(email);
			return this;
		}
		@Override
		public CustomerBuilder addPhone(String phone) {
			((Customer) this.user).setPhone(phone);
			return this;
		}
	}
	
	
	
	public static class RestaurantBuilder extends LocalizedUserBuilder {
		private static RestaurantBuilder restaurantBuilder;
		
		private static UserBuilder getInstance() {
			if (restaurantBuilder == null) 
				restaurantBuilder = new RestaurantBuilder();
			return restaurantBuilder.reset();
		}
		@Override
		public UserBuilder reset() {
			this.user = new Restaurant();
			return this;
		}
		@Override
		public UserBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Restaurant);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder addGenericDiscountFactor(Double genericDiscountFactor) {
			((Restaurant) this.user).setGenericDiscountFactor(genericDiscountFactor);
			return this;
		}
		@Override
		public UserBuilder addSpecialDiscountFactor(Double specialDiscountFactor) {
			((Restaurant) this.user).setSpecialDiscountFactor(specialDiscountFactor);
			return this;
		}
	}
		

	
	public static class CourierBuilder extends LocalizedUserBuilder {
		private static CourierBuilder courierBuilder;
		
		private static UserBuilder getInstance() {
			if (courierBuilder == null) 
				courierBuilder = new CourierBuilder();
			return courierBuilder.reset();
		}
		@Override
		public UserBuilder reset() {
			this.user = new Courier();
			return this;
		}
		@Override
		public UserBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Courier);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public UserBuilder addSurname(String surname) {
			((Courier) this.user).setSurname(surname);
			return this;
		}
		@Override
		public UserBuilder addPhone(String phone) {
			((Courier) this.user).setPhone(phone);
			return this;
		}
	}
}
