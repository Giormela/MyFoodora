package myFoodora.tests.food;

import myFoodora.entities.Location;
import myFoodora.entities.food.Dish;
import myFoodora.services.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.DishType;
import myFoodora.enums.FoodCategory;

public class DishTest {
    private Dish dish;
    private Restaurant restaurant;

    @BeforeEach
    void setUp() {
        restaurant = UserBuilder.buildUserOfType(Restaurant.class)
                .addName("My restaurant")
                .addLocation(Location.convertFromAdressToCoordinates("1 rue des Champs Elysées, 75008 Paris, France"))
                .addCredential("ceo", "123456789")
                .getResult();
        dish = new Dish(restaurant, "Pasta", DishType.MainDish, FoodCategory.Standard, 15.00);
    }

    @Test
    void testGetPrice() {
        // Testing the getPrice method
        assertEquals(15.00, dish.getPrice(), "Price should be 15.00");
    }

    @Test
    void testGetDishType() {
        // Testing the getDishType method
        assertEquals(DishType.MainDish, dish.getDishType(), "Dish type should be MainDish");
    }

    @Test
    void testSetDishType() {
        // Setting a new dish type
        dish.setDishType(DishType.Starter);
        // Testing the new dish type
        assertEquals(DishType.Starter, dish.getDishType(), "Dish type should be updated to Starter");
    }

    @Test
    void testGetFoodCategory() {
        // Testing the getFoodCategory method
        assertEquals(FoodCategory.Standard, dish.getFoodCategory(), "Food category should be Standard");
    }

    @Test
    void testSetFoodCategory() {
        // Setting a new food category
        dish.setFoodCategory(FoodCategory.Vegeterian);
        // Testing the new food category
        assertEquals(FoodCategory.Vegeterian, dish.getFoodCategory(), "Food category should be updated to Vegetarian");
    }

    @Test
    void testGetRestaurant() {
        // Testing the getRestaurant method
        assertEquals(restaurant, dish.getRestaurant(), "Restaurant should be the same as the one set in the constructor");
    }

    @Test
    void testGetName() {
        // Testing the getName method
        assertEquals("Pasta", dish.getName(), "Name should be Pasta");
    }
}
