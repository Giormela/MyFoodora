package myFoodora.exceptions;

/**
 * The class MealCreationException represents an exception that is thrown when a
 * meal cannot be created.
 *
 * @see CommandException
 */
public class MealCreationException extends CommandException {

    public MealCreationException(String message) {
        super(message);
    }

    public String getMessage() {
        return "Meal creation failed: " + super.message;
    }
}
