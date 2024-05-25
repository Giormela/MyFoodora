package myFoodora.entities.fidelityCard;

import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.FidelityCardType;

public abstract class FidelityCard {
    protected Restaurant restaurant;
    protected Customer customer;
    protected FidelityCardType type;

    public Restaurant getAssociatedRestaurant() {
        return this.restaurant;
    }

    public Customer getAssociatedCustomer() {
        return this.customer;
    }

    public abstract Double applyDiscount(Double fullPrice, Customer customer);
}
