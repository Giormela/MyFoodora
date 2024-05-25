package myFoodora.entities.fidelityCard;

import myFoodora.entities.user.Customer;
import myFoodora.entities.user.Restaurant;
import myFoodora.enums.FidelityCardType;

import java.util.Random;

public class LotteryFidelityCard extends FidelityCard {
    private static final Random random = new Random();

    public LotteryFidelityCard(Restaurant restaurant, Customer customer) {
        this.restaurant = restaurant;
        this.customer = customer;
        this.type = FidelityCardType.Lottery;
    }

    @Override
    public Double applyDiscount(Double fullPrice, Customer customer) {
        if (random.nextDouble() < 0.01) {
            return 0.0;
        }
        return fullPrice;
    }
}

