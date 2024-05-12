package fr.cs.oose.myFoodora.user;

public abstract class UserBuilder{	
	private static UserBuilder.ManagerBuilder managerBuilder;
	private static UserBuilder.CustomerBuilder customerBuilder;
	private static UserBuilder.RestaurantBuilder restaurantBuilder;
	private static UserBuilder.CourierBuilder courierBuilder;
	
	protected User user;
	
	protected abstract UserBuilder reset();
	
	protected abstract UserBuilder addCredential(String username, String password);
	
	public static ManagerBuilder buildManager() {
		if (managerBuilder == null) 
			managerBuilder = new ManagerBuilder();
		return managerBuilder.reset(); 
	}
	
	public static CustomerBuilder buildCustomer() {
		if (customerBuilder == null) 
			customerBuilder = new CustomerBuilder();
		return customerBuilder.reset();
	}
	
	public static RestaurantBuilder buildRestaurant() {
		if (restaurantBuilder == null) 
			restaurantBuilder = new RestaurantBuilder();
		return restaurantBuilder.reset(); 
	}
	
	public static CourierBuilder buildCourier() {
		if (courierBuilder == null) 
			courierBuilder = new CourierBuilder();
		return courierBuilder.reset(); 
	}
	
	public UserBuilder addName(String name) {
		this.user.setName(name);
		return this;
	}

	public User getResult() {
		return this.user;
	}
	
	public static class ManagerBuilder extends UserBuilder{
		@Override
		public Manager getResult() {
			return (Manager) super.getResult();
		}
		@Override
		protected ManagerBuilder reset() {
			this.user = new Manager();
			return this;
		}
		@Override
		public  ManagerBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Manager);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public ManagerBuilder addName(String name) {
			super.addName(name);
			return this;
		}
		public ManagerBuilder addSurname(String surname) {
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
		@Override
		public Customer getResult() {
			return (Customer) super.getResult();
		}
		@Override
		protected CustomerBuilder reset() {
			this.user = new Customer();
			return this;
		}
		@Override
		public CustomerBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Customer);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public CustomerBuilder addName(String name) {
			super.addName(name);
			return this;
		}
		@Override
		public CustomerBuilder addLocation(Location location) {
			super.addLocation(location);
			return this;
		}
		public CustomerBuilder addSurname(String surname) {
			((Customer) this.user).setSurname(surname);
			return this;
		}
		public CustomerBuilder addEmail(String email) {
			((Customer) this.user).setEmail(email);
			return this;
		}
		public CustomerBuilder addPhone(String phone) {
			((Customer) this.user).setPhone(phone);
			return this;
		}
	}
	
	
	
	public static class RestaurantBuilder extends LocalizedUserBuilder {
		@Override
		public Restaurant getResult() {
			return (Restaurant) super.getResult();
		}
		@Override
		protected RestaurantBuilder reset() {
			this.user = new Restaurant();
			return this;
		}
		@Override
		public RestaurantBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Restaurant);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public RestaurantBuilder addName(String name) {
			super.addName(name);
			return this;
		}
		@Override
		public RestaurantBuilder addLocation(Location location) {
			super.addLocation(location);
			return this;
		}
		public RestaurantBuilder addGenericDiscountFactor(Double genericDiscountFactor) {
			((Restaurant) this.user).setGenericDiscountFactor(genericDiscountFactor);
			return this;
		}
		public RestaurantBuilder addSpecialDiscountFactor(Double specialDiscountFactor) {
			((Restaurant) this.user).setSpecialDiscountFactor(specialDiscountFactor);
			return this;
		}
	}
		

	
	public static class CourierBuilder extends LocalizedUserBuilder {
		@Override
		public Courier getResult() {
			return (Courier) super.getResult();
		}
		@Override
		protected CourierBuilder reset() {
			this.user = new Courier();
			return this;
		}
		@Override
		public CourierBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Courier);
			this.user.setCredential(credential);
			return this;
		}
		@Override
		public CourierBuilder addName(String name) {
			super.addName(name);
			return this;
		}
		@Override
		public CourierBuilder addLocation(Location location) {
			super.addLocation(location);
			return this;
		}
		public CourierBuilder addSurname(String surname) {
			((Courier) this.user).setSurname(surname);
			return this;
		}
		public CourierBuilder addPhone(String phone) {
			((Courier) this.user).setPhone(phone);
			return this;
		}
	}
}
