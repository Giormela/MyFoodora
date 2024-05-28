package myFoodora.entities.fidelityCard;

public class PointFidelityCard implements FidelityCard {
    private int points;

    @Override
    public Double apply(Double fullPrice) {
        addPoints((int) (fullPrice / 10));

        if (points >= 100) {
            removePoints(100);
            return fullPrice * 0.9;
        }
        return fullPrice;
    }

    private void addPoints(int pointsToAdd) {
        this.points += pointsToAdd;
    }
    
    private void removePoints(int pointsToAdd) {
        this.points -= pointsToAdd;
    }
}
