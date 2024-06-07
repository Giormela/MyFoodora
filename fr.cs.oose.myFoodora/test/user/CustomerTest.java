package user;

import static org.junit.jupiter.api.Assertions.*;

import myFoodora.entities.Location;
import myFoodora.entities.food.Dish;
import myFoodora.entities.food.MealBuilder;
import myFoodora.enums.DishType;
import myFoodora.enums.FoodCategory;
import myFoodora.exceptions.MealCreationException;
import myFoodora.services.UserBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import myFoodora.entities.Date;
import myFoodora.entities.Order;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.exceptions.ElementNotFoundException;

class CustomerTest {
    private Customer customer;
    private Order order;
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

        starter = new Dish(restaurant, "Soup", DishType.Starter, FoodCategory.Standard, 5.0);
        main = new Dish(restaurant, "Steak", DishType.MainDish, FoodCategory.Standard, 15.0);
        dessert = new Dish(restaurant, "Cake", DishType.Dessert, FoodCategory.Standard, 7.0);

        restaurant.addDish(starter);
        restaurant.addDish(main);
        restaurant.addDish(dessert);

        restaurant.createMeal("Meal 1");
        restaurant.addDishToMeal("Meal 1", "Soup");
        restaurant.addDishToMeal("Meal 1", "Steak");
        restaurant.addDishToMeal("Meal 1", "Cake");

        restaurant.saveMeal("Meal 1");

        order = new Order(customer, restaurant, "Order1");
    }


    @Test
    void testPrepareNewOrder() {
        assertEquals(0, customer.getCart().size());
        customer.prepareNewOrder(order);
        assertEquals(1, customer.getCart().size(), "Cart should have one order after preparation.");
        assertTrue(customer.getCart().containsKey(order.getName()));
    }

    @Test
    void testAddFoodToOrder() throws ElementNotFoundException {
        customer.prepareNewOrder(order);
        customer.addFoodToOrder("Order1", "Meal 1");  // adding once
        assertEquals(1, order.getFood().size(), "Order should contain one instance of the food after addition.");
    }

    @Test
    void testPayOrder() throws ElementNotFoundException {
        customer.prepareNewOrder(order);
        customer.addFoodToOrder("Order1", "Meal 1");
        Order paidOrder = customer.payOrder("Order1", new Date(2024/* year */, 12/* month */, 31/* day */));
        assertNotNull(paidOrder, "Paid order should not be null.");
        assertEquals(0, customer.getCart().size(), "Cart should be empty after paying for the order.");
        assertTrue(paidOrder.getPricePayed() > 0, "Price paid should be more than zero.");
    }

    @Test
    void testPayOrderThrowsElementNotFoundException() {
        ElementNotFoundException thrown = assertThrows(ElementNotFoundException.class, () -> {
            customer.payOrder("NonExistentOrder", new Date(2024/* year */, 12/* month */, 31/* day */));
        }, "ElementNotFoundException should be thrown for non-existent order.");
        assertTrue(thrown.getMessage().contains("There is no order named NonExistentOrder in your cart"));
    }

    @Test
    void testAddFoodToNonexistentOrderThrowsException() {
        assertThrows(ElementNotFoundException.class, () -> {
            customer.addFoodToOrder("NonExistentOrder", "Meal 1");
        }, "Trying to add food to a non-existent order should throw an exception.");
    }
}
