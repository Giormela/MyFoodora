package myFoodora.entities.Fidelity;

import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.FidelityCardType;

public class BasicFidelityCard extends FidelityCard {

    public BasicFidelityCard(Restaurant restaurant, Customer customer) {
        this.restaurant = restaurant;
        this.customer = customer;
        this.type = FidelityCardType.Basic;
    }

    @Override
    public Double applyDiscount(Double fullPrice, Customer customer) {
        // Check for any special offers available and apply them
        // For now, it returns the full price indicating no discount
        return fullPrice;
    }
}
