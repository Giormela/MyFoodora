package myFoodora.entities.food;

public class MealOfTheWeek {
    private Boolean isMealOfTheWeek;
    private Double discountFactor;

    public MealOfTheWeek(Boolean isMealOfTheWeek, Double discountFactor) {
        this.isMealOfTheWeek = isMealOfTheWeek;
        this.discountFactor = discountFactor;
    }

    public MealOfTheWeek(Boolean isMealOfTheWeek) {
        this.isMealOfTheWeek = isMealOfTheWeek;
        this.discountFactor = 0.1;
    }

    public Boolean getMealOfTheWeek() {
        return isMealOfTheWeek;
    }

    public void setMealOfTheWeek(Boolean mealOfTheWeek) {
        isMealOfTheWeek = mealOfTheWeek;
    }

    public Double getDiscountFactor() {
        return discountFactor;
    }

    public void setDiscountFactor(Double discountFactor) {
        this.discountFactor = discountFactor;
    }
}
