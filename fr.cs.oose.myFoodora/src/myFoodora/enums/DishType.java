package myFoodora.enums;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

public enum DishType {
    Starter, MainDish, Dessert;
    
    public static DishType fromString(String string) throws NoSuchElementException {
    	return Stream.of(DishType.values())
    			.filter(dt->dt.toString().equals(string))
    			.findAny()
    			.orElseThrow();  
    }
}
