package myFoodora.exceptions;

public class CommandException extends Exception {
	public String message;

	public CommandException(String message) {
		super();
		this.message = message;
	}
	
}
