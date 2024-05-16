package myFoodora.entities.food;


import myFoodora.enums.foodEnum.CategoryEnum;
import myFoodora.enums.foodEnum.MealCategory;
import myFoodora.enums.foodEnum.MealType;


public class Meal{
    private MealCategory mealCategory; // Standard, Vegetarian, GlutenFree, VegetarianGlutenFree
    private MealType mealType; // Half_Meal, Full_Meal
    private Double price;
    private Dish[] dishes;
    private MealOfTheWeek mealOfTheWeek;

    public Meal(Dish starterOrDessert, Dish mainDish) {
        if (starterOrDessert.isVegetarian() && starterOrDessert.isGlutenFree() && mainDish.isVegetarian() && mainDish.isGlutenFree()) {
            this.mealCategory = MealCategory.VegetarianGlutenFree;
        } else if (starterOrDessert.isGlutenFree() && mainDish.isGlutenFree()) {
            this.mealCategory = MealCategory.GlutenFree;
        } else if (starterOrDessert.isVegetarian() && mainDish.isVegetarian()) {
            this.mealCategory = MealCategory.Vegetarian;
        } else {
            this.mealCategory = MealCategory.Standard;
        }
        this.mealType = MealType.Half_Meal;
        this.price = (starterOrDessert.getPrice() + mainDish.getPrice()) * 0.95;
        this.dishes = new Dish[]{starterOrDessert, mainDish};
    }

    public Meal(Dish starterOrDessert, Dish mainDish, MealOfTheWeek mealOfTheWeek) {
        if (starterOrDessert.isVegetarian() && starterOrDessert.isGlutenFree() && mainDish.isVegetarian() && mainDish.isGlutenFree()) {
            this.mealCategory = MealCategory.VegetarianGlutenFree;
        } else if (starterOrDessert.isGlutenFree() && mainDish.isGlutenFree()) {
            this.mealCategory = MealCategory.GlutenFree;
        } else if (starterOrDessert.isVegetarian() && mainDish.isVegetarian()) {
            this.mealCategory = MealCategory.Vegetarian;
        } else {
            this.mealCategory = MealCategory.Standard;
        }
        this.mealType = MealType.Half_Meal;
        if (mealOfTheWeek.getMealOfTheWeek()) {
            this.price = (starterOrDessert.getPrice() + mainDish.getPrice()) * (1-mealOfTheWeek.getDiscountFactor());
        } else {
            this.price = (starterOrDessert.getPrice() + mainDish.getPrice()) * 0.95;
        }
        this.dishes = new Dish[]{starterOrDessert, mainDish};
        this.mealOfTheWeek = mealOfTheWeek;
    }

    public Meal(Dish starter, Dish mainDish, Dish dessert) {
        if (starter.isVegetarian() && starter.isGlutenFree() && mainDish.isVegetarian() && mainDish.isGlutenFree() && dessert.isVegetarian() && dessert.isGlutenFree()) {
            this.mealCategory = MealCategory.VegetarianGlutenFree;
        } else if (starter.isGlutenFree() && mainDish.isGlutenFree() && dessert.isGlutenFree()) {
            this.mealCategory = MealCategory.GlutenFree;
        } else if (starter.isVegetarian() && mainDish.isVegetarian() && dessert.isVegetarian()) {
            this.mealCategory = MealCategory.Vegetarian;
        } else {
            this.mealCategory = MealCategory.Standard;
        }
        this.mealType = MealType.Full_Meal;
        this.price = (starter.getPrice() + mainDish.getPrice() + dessert.getPrice()) * 0.95;
        this.dishes = new Dish[]{starter, mainDish, dessert};
    }

    public Meal(Dish starter, Dish mainDish, Dish dessert, MealOfTheWeek mealOfTheWeek) {
        if (starter.isVegetarian() && starter.isGlutenFree() && mainDish.isVegetarian() && mainDish.isGlutenFree() && dessert.isVegetarian() && dessert.isGlutenFree()) {
            this.mealCategory = MealCategory.VegetarianGlutenFree;
        } else if (starter.isGlutenFree() && mainDish.isGlutenFree() && dessert.isGlutenFree()) {
            this.mealCategory = MealCategory.GlutenFree;
        } else if (starter.isVegetarian() && mainDish.isVegetarian() && dessert.isVegetarian()) {
            this.mealCategory = MealCategory.Vegetarian;
        } else {
            this.mealCategory = MealCategory.Standard;
        }
        this.mealType = MealType.Full_Meal;
        if (mealOfTheWeek.getMealOfTheWeek()) {
            this.price = (starter.getPrice() + mainDish.getPrice() + dessert.getPrice()) * (1- mealOfTheWeek.getDiscountFactor());
        } else {
            this.price = (starter.getPrice() + mainDish.getPrice() + dessert.getPrice()) * 0.95;
        }
        this.dishes = new Dish[]{starter, mainDish, dessert};
        this.mealOfTheWeek = mealOfTheWeek;
    }

    public static void main(String[] args) {
        Dish starterOrDessert = new Dish("Salad", 5.0, CategoryEnum.Starter, true, false);
        Dish mainDish = new Dish("Pasta", 10.0, CategoryEnum.MainDish, false, false);
        Dish dessert = new Dish("Ice cream", 3.0, CategoryEnum.Dessert, true, true);
        Meal halfMeal = new Meal(starterOrDessert, mainDish, dessert);
        System.out.println(halfMeal.price);
        System.out.println(halfMeal.mealCategory);
        System.out.println(halfMeal.mealType);
        System.out.println(halfMeal.dishes[0].getName());
        System.out.println(halfMeal.dishes[1].getName());
        System.out.println(halfMeal.dishes[2].getName());

        System.out.println("----------------");
        MealOfTheWeek mealOfTheWeek = new MealOfTheWeek(true, 0.1);
        Meal halfMeal2 = new Meal(starterOrDessert, mainDish, mealOfTheWeek);
        System.out.println(halfMeal2.price);
        System.out.println(halfMeal2.mealCategory);
        System.out.println(halfMeal2.mealType);
        System.out.println(halfMeal2.dishes[0].getName());
        System.out.println(halfMeal2.dishes[1].getName());
        System.out.println(halfMeal2.mealOfTheWeek.getMealOfTheWeek());
        System.out.println(halfMeal2.mealOfTheWeek.getDiscountFactor());
    }
}
