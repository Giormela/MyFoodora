package myFoodora.exceptions;

public class ElementNotFoundException extends CommandException {
	
	public ElementNotFoundException(String message) {
        super(message);
    }

    public String getMessage() {
        return "Element not found: " + super.message;
    }
}
