package myFoodora.exceptions;

public class UserRegistrationException extends Exception {
	public String message;

	public UserRegistrationException(String message) {
		super();
		this.message = message;
	}
	
}
