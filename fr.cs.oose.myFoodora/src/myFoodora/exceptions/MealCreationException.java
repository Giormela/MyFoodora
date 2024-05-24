package myFoodora.exceptions;

public class MealCreationException extends Throwable {
    public String message;

    public MealCreationException(String message) {
        super();
        this.message = message;
    }
}
