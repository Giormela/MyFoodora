package myFoodora.entities.fidelityCard;

public class BasicFidelityCard implements FidelityCard {
    @Override
    public Double apply(Double fullPrice) {
        // Check for any special offers available and apply them
        // For now, it returns the full price indicating no discount
        return fullPrice;
    }
}
