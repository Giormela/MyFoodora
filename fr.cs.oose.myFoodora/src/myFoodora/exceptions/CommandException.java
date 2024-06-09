package myFoodora.exceptions;

public class CommandException extends Throwable {
	protected String message;

	public CommandException(String message) {
		super();
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
}
