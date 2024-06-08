package myFoodora.tests;

import static org.junit.jupiter.api.Assertions.*;
import myFoodora.entities.Location;
import myFoodora.entities.Order;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.services.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import myFoodora.entities.food.Dish;
import myFoodora.enums.DishType;
import myFoodora.enums.FoodCategory;
import myFoodora.exceptions.ElementNotFoundException;
import myFoodora.exceptions.MealCreationException;

class RestaurantTest {
    private Customer customer;
    private Restaurant restaurant;
    private Dish starter, main, dessert;

    @BeforeEach
    void setUp() throws MealCreationException, ElementNotFoundException {
        customer = new Customer();
        restaurant = UserBuilder.buildUserOfType(Restaurant.class)
                .addName("My restaurant")
                .addLocation(Location.convertFromAdressToCoordinates("1 rue des Champs Elysées, 75008 Paris, France"))
                .addCredential("ceo", "123456789")
                .getResult();

        starter = new Dish(restaurant, "Salad", DishType.Starter, FoodCategory.Standard, 5.0);
        main = new Dish(restaurant, "Pizza", DishType.MainDish, FoodCategory.Standard, 15.0);
        dessert = new Dish(restaurant, "Cheesecake", DishType.Dessert, FoodCategory.Standard, 7.0);
        restaurant.addDish(starter);
        restaurant.addDish(main);
        restaurant.addDish(dessert);
    }

    @Test
    void testAddAndRemoveDish() throws ElementNotFoundException {
        assertNotNull(restaurant.getFood("Pizza"));
        restaurant.removeDish("Pizza");
        assertThrows(ElementNotFoundException.class, () -> restaurant.getFood("Pizza"));
    }

    @Test
    void testCreateAndSaveMeal() throws MealCreationException, ElementNotFoundException {
        restaurant.createMeal("Lunch Special");
        restaurant.addDishToMeal("Lunch Special", "Pizza");
        restaurant.addDishToMeal("Lunch Special", "Cheesecake");
        restaurant.saveMeal("Lunch Special");
        assertNotNull(restaurant.getFood("Lunch Special"));
    }

    @Test
    void testMealCreationException() {
        assertThrows(MealCreationException.class, () -> {
            restaurant.createMeal("Lunch Special");
            restaurant.createMeal("Lunch Special"); // Trying to create the same meal again
        });
    }

    @Test
    void testDisplayMeal() throws ElementNotFoundException, MealCreationException {
        restaurant.createMeal("Dinner Special");
        restaurant.addDishToMeal("Dinner Special", "Salad");
        restaurant.addDishToMeal("Dinner Special", "Pizza");
        restaurant.saveMeal("Dinner Special");
        String displayOutput = restaurant.displayMeal("Dinner Special");
        assertTrue(displayOutput.contains("Salad"));
    }
}
