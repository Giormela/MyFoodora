package myFoodora.enums;

import java.util.stream.Stream;

import myFoodora.exceptions.ElementNotFoundException;

public enum DishType {
    Starter, MainDish, Dessert;
    
    public static DishType fromString(String string) throws ElementNotFoundException {
    	return Stream.of(DishType.values())
    			.filter(dt->dt.toString().toLowerCase().equals(string.toLowerCase()))
    			.findAny()
    			.orElseThrow(()->new ElementNotFoundException("DishType not found"));
    			 
    }
}
