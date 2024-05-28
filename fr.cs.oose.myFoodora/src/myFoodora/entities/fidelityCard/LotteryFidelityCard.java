package myFoodora.entities.fidelityCard;

import java.util.Random;

public class LotteryFidelityCard implements FidelityCard {
    private static final Random random = new Random();

    @Override
    public Double apply(Double fullPrice) {
        if (random.nextDouble() < 0.01) {
            return 0.0;
        }
        return fullPrice;
    }
}

