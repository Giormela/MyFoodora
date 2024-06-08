package myFoodora.tests;

import static org.junit.jupiter.api.Assertions.*;

import myFoodora.entities.Order;
import myFoodora.entities.user.Courier;
import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.CourierState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CourierTest {
    private Courier courier;
    private Order order;
    private Customer customer;  // Assuming Customer has a minimal setup
    private Restaurant restaurant;  // Assuming Restaurant has a minimal setup

    @BeforeEach
    void setUp() {
        customer = new Customer();  // You'll need to setup this according to your Customer class
        restaurant = new Restaurant();  // You'll need to setup this according to your Restaurant class
        courier = new Courier();
        order = new Order(customer, restaurant, "Test Order");
    }

    @Test
    void testAssignOrder() {
        assertNull(courier.getAssignedOrder().orElse(null), "Initially, courier should not have an assigned order.");
        courier.assignOrder(order);
        assertTrue(courier.getAssignedOrder().isPresent(), "Courier should have an assigned order after assignOrder.");
        assertEquals(order, courier.getAssignedOrder().get(), "The assigned order should match the order given.");
        assertEquals(CourierState.OnDuty, courier.getState(), "Courier state should be OnDuty after order assignment.");
    }

    @Test
    void testSetState() {
        courier.setState(CourierState.OnDuty);
        assertEquals(CourierState.OnDuty, courier.getState(), "Courier state should be updated to OnDuty.");
    }

    @Test
    void testOrderCount() {
        assertEquals(0, courier.getOrderCount(), "New courier should have completed zero orders.");
        courier.addOrderToHistory(order);
        assertEquals(1, courier.getOrderCount(), "Courier should have one completed order after adding to history.");
    }
}
