package myFoodora.exceptions;

public class CommandException extends Throwable {
	public String message;

	public CommandException(String message) {
		super();
		this.message = message;
	}
	
}
