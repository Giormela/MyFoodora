package myFoodora.enums;

import java.util.stream.Stream;

import myFoodora.exceptions.ElementNotFoundException;

public enum FoodCategory {
	Standard, Vegeterian, GlutenFree;
	
	public static FoodCategory fromString(String string) throws ElementNotFoundException {
    	return Stream.of(FoodCategory.values())
    			.filter(dt->dt.toString().toLowerCase().equals(string.toLowerCase()))
    			.findAny()
    			.orElseThrow(()->new ElementNotFoundException("DishType not found"));  
    }
}
