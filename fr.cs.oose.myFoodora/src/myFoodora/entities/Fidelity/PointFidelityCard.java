package myFoodora.entities.Fidelity;

import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.FidelityCardType;

public class PointFidelityCard extends FidelityCard {
    private int points = 0;

    public PointFidelityCard(Restaurant restaurant, Customer customer) {
        this.restaurant = restaurant;
        this.customer = customer;
        this.type = FidelityCardType.Point;
    }

    @Override
    public Double applyDiscount(Double fullPrice, Customer customer) {
        addPoints((int) (fullPrice / 10));

        if (points >= 100) {
            resetPoints();
            return fullPrice * 0.9;
        }
        return fullPrice;
    }

    private void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }

    private void resetPoints() {
        this.points = 0;
    }
}
