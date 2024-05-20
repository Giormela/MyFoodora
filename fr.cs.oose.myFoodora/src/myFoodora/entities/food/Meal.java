package myFoodora.entities.food;


import java.util.Collection;

import myFoodora.enums.DishType;
import myFoodora.enums.MealCategory;
import myFoodora.enums.MealType;


public class Meal extends Food{
    //private MealCategory mealCategory; // Standard, Vegetarian, GlutenFree, VegetarianGlutenFree
    private MealType mealType; // Half_Meal, Full_Meal
	private Double price;
    private Collection<Dish> dishes;
    private Boolean mealOfTheWeek;
    
    
    public Meal(Collection<Dish> dishes) {//throws Exception{
    	if(dishes.size()!=2 && dishes.size()!=3) {
    		//throw new Exception();
    	}
    	if(dishes.size()==2) {
    		this.mealType = MealType.Half_Meal;
    	}
    	if(dishes.size()==3) {
    		this.mealType = MealType.Full_Meal;
    	}
    	this.dishes = dishes;
    	this.vegeterian = dishes.stream().allMatch(Dish::isVegetarian);
    	this.glutenFree = dishes.stream().allMatch(Dish::isGlutenFree);
    	this.mealOfTheWeek = false;
    }
    
  

    

    public static void main(String[] args) {
        Dish starterOrDessert = new Dish("Salad", 5.0, DishType.Starter, true, false);
        Dish mainDish = new Dish("Pasta", 10.0, DishType.MainDish, false, false);
        Dish dessert = new Dish("Ice cream", 3.0, DishType.Dessert, true, true);
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
        System.out.println(halfMeal2.mealOfTheWeek.isMealOfTheWeek());
        System.out.println(halfMeal2.mealOfTheWeek.getDiscountFactor());
    }

	@Override
	public Double getPrice() {
		// TODO Auto-generated method stub
		//
		//this.restaurant.getGenericDiscountFactor();
		return null;
	}
	
	public Boolean isvegeterianAndGlutenFree() {
		return vegeterian && glutenFree;
	}
	
	 public MealType getMealType() {
			return mealType;
		}

}
