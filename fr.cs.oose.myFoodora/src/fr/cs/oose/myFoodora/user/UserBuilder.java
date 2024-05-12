package fr.cs.oose.myFoodora.user;

public abstract class UserBuilder{	
	private static UserBuilder.CustomerBuilder customerBuilder = new UserBuilder.CustomerBuilder();
	
	protected User user;
	
	public static CustomerBuilder buildCustomer() {
		return customerBuilder.reset();
	}
	
	protected abstract UserBuilder addCredential(String username, String password);
	
	protected abstract UserBuilder reset();
	
	public UserBuilder addName(String name) {
		this.user.setName(name);
		return this;
	}
	
	public User getResult() {
		return this.user;
	}
	
	
	public static class CustomerBuilder extends UserBuilder {
	
		@Override
		public CustomerBuilder reset() {
			this.user = new Customer();
			return this;
		}
		
		@Override
		protected CustomerBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Customer);
			this.user.setCredential(credential);
			return this;
		}
		
		public CustomerBuilder addSurname(String surname) {
			((Customer) this.user).setSurname(surname);
			return this;
		}

	}
	
	public static class ManagerBuilder extends UserBuilder{

		@Override
		protected UserBuilder addCredential(String username, String password) {
			Credential credential = new Credential(username, password, UserType.Manager);
			this.user.setCredential(credential);
			return this;
		}

		@Override
		protected UserBuilder reset() {
			// TODO Auto-generated method stub
			return null;
		}
		
	}

}
