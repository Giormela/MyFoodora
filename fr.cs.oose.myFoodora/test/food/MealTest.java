
import myFoodora.entities.Location;
import myFoodora.entities.food.Dish;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.DishType;
import myFoodora.enums.FoodCategory;
import myFoodora.services.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import myFoodora.entities.food.Meal;
import myFoodora.entities.food.MealBuilder;
import myFoodora.enums.MealType;
import myFoodora.exceptions.MealCreationException;

class MealTest {
    private Restaurant restaurant;
    private Dish starter, main, dessert;
    private MealBuilder builder;

    @BeforeEach
    void setUp() {
        restaurant = UserBuilder.buildUserOfType(Restaurant.class)
                .addName("My restaurant")
                .addLocation(Location.convertFromAdressToCoordinates("1 rue des Champs Elysées, 75008 Paris, France"))
                .addCredential("ceo", "123456789")
                .getResult();
        starter = new Dish(restaurant, "Soup", DishType.Starter, FoodCategory.Standard, 5.0);
        main = new Dish(restaurant, "Steak", DishType.MainDish, FoodCategory.Standard, 15.0);
        dessert = new Dish(restaurant, "Cake", DishType.Dessert, FoodCategory.Standard, 7.0);
        builder = new MealBuilder(restaurant, "Test Meal");
    }

    @Test
    void testFullMealCreation() throws MealCreationException {
        builder.addDish(starter);
        builder.addDish(main);
        builder.addDish(dessert);
        Meal meal = builder.build();
        assertEquals(MealType.Full_Meal, meal.getMealType());
        assertTrue(meal.getDishes().containsAll(Arrays.asList(starter, main, dessert)));
        assertEquals(3, meal.getDishes().size());
    }

    @Test
    void testHalfMealCreation() throws MealCreationException {
        builder.addDish(starter);
        builder.addDish(main);
        Meal meal = builder.build();
        assertEquals(MealType.Half_Meal, meal.getMealType());
        assertTrue(meal.getDishes().containsAll(Arrays.asList(starter, main)));
        assertEquals(2, meal.getDishes().size());
    }

    @Test
    void testMealCreationExceptionForDuplicateDishType() {
        assertDoesNotThrow(() -> builder.addDish(starter));
        MealCreationException exception = assertThrows(MealCreationException.class, () -> {
            builder.addDish(new Dish(restaurant, "Another Soup", DishType.Starter, FoodCategory.Standard, 6.0));
        });
        assertTrue(exception.getMessage().contains("already present in the meal"));
    }

    @Test
    void testPriceCalculationWithGenericDiscount() throws MealCreationException {
        builder.addDish(starter);
        builder.addDish(main);
        Meal meal = builder.build();
        assertFalse(meal.isMealOfTheWeek());
        double expectedPrice = (5.0 + 15.0) * (1 - 5.0 / 100); // Generic discount
        assertEquals(expectedPrice, meal.getPrice());
    }

    @Test
    void testPriceCalculationWithSpecialDiscount() throws MealCreationException {
        builder.addDish(starter);
        builder.addDish(main);
        Meal meal = builder.build();
        meal.setMealOfTheWeek(true);
        double expectedPrice = (5.0 + 15.0) * (1 - 10.0 / 100);
        assertEquals(expectedPrice, meal.getPrice());
    }

    @Test
    void testAddingDuplicateDishThrowsException() throws MealCreationException {
        // First add is expected to succeed
        builder.addDish(starter);

        // Second add should fail
        MealCreationException exception = assertThrows(MealCreationException.class, () -> {
            builder.addDish(starter);
        });

        // Verify the message in the exception
        assertTrue(exception.getMessage().contains("Dish already present in the meal"));
    }

    @Test
    void testMealCreationExceptionForDifferentRestaurant() {
        // Setup a dish from another restaurant
        Restaurant anotherRestaurant = UserBuilder.buildUserOfType(Restaurant.class)
                .addName("Another restaurant")
                .addLocation(Location.convertFromAdressToCoordinates("2 rue des Champs Elysées, 75008 Paris, France"))
                .addCredential("admin", "password")
                .getResult();
        Dish externalDish = new Dish(anotherRestaurant, "Foreign Dish", DishType.Starter, FoodCategory.Standard, 6.0);

        // Attempt to add the external dish to the meal
        MealCreationException exception = assertThrows(MealCreationException.class, () -> {
            builder.addDish(externalDish);
        });

        // Check that the correct exception message is produced
        assertTrue(exception.getMessage().contains("Meal and Dish are not part of the same restaurant"));
    }

    @Test
    void testMealFoodCategoryAllVegetarian() throws MealCreationException {
        Dish vegetarianStarter = new Dish(restaurant, "Veggie Starter", DishType.Starter, FoodCategory.Vegeterian, 5.0);
        Dish vegetarianMain = new Dish(restaurant, "Veggie Main", DishType.MainDish, FoodCategory.Vegeterian, 15.0);

        builder.addDish(vegetarianStarter);
        builder.addDish(vegetarianMain);
        Meal meal = builder.build();

        assertEquals(FoodCategory.Vegeterian, meal.getFoodCategory());
    }

    @Test
    void testMealFoodCategoryAllGlutenFree() throws MealCreationException {
        Dish glutenFreeStarter = new Dish(restaurant, "Gluten-Free Soup", DishType.Starter, FoodCategory.GlutenFree, 5.0);
        Dish glutenFreeMain = new Dish(restaurant, "Gluten-Free Steak", DishType.MainDish, FoodCategory.GlutenFree, 15.0);

        builder.addDish(glutenFreeStarter);
        builder.addDish(glutenFreeMain);
        Meal meal = builder.build();

        assertEquals(FoodCategory.GlutenFree, meal.getFoodCategory());
    }

    @Test
    void testMealFoodCategoryMixed() throws MealCreationException {
        Dish vegetarianStarter = new Dish(restaurant, "Veggie Starter", DishType.Starter, FoodCategory.Vegeterian, 5.0);
        Dish standardMain = new Dish(restaurant, "Regular Steak", DishType.MainDish, FoodCategory.Standard, 15.0);

        builder.addDish(vegetarianStarter);
        builder.addDish(standardMain);
        Meal meal = builder.build();

        assertEquals(FoodCategory.Standard, meal.getFoodCategory());
    }
}

