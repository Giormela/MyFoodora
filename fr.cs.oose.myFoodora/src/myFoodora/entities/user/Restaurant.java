package myFoodora.entities.user;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import myFoodora.entities.Order;
import myFoodora.entities.fidelityCard.FidelityCard;
import myFoodora.entities.food.Dish;
import myFoodora.entities.food.Food;
import myFoodora.entities.food.Meal;
import myFoodora.enums.DishType;
import myFoodora.enums.MealType;
import myFoodora.exceptions.MealCreationException;

public class Restaurant extends LocalizedUser {
    private Double genericDiscountFactor;
    private Double specialDiscountFactor;
    private Map<Customer, FidelityCard> fidelityCards;
    private Map<String, Dish> menu;
    private Map<Integer, Meal> meals;

    public Restaurant() {
        super();
        this.genericDiscountFactor = 5.0;
        this.specialDiscountFactor = 10.0;
        this.fidelityCards = new HashMap<Customer, FidelityCard>();
        this.menu = new HashMap<String, Dish>();
        this.meals = new HashMap<Integer, Meal>();
    }

    public Collection<Meal> getSortedMeals() {
        Stream<Meal> allMealsOrdered = orderHistory.stream()
                .flatMap(Order::getMeals)
                .filter(m -> m.getMealType() == MealType.Half_Meal);
        return sortByFrequence(allMealsOrdered);
    }

    public Collection<Dish> getSortedDishes() {
        Stream<Dish> allDishesOrdered = orderHistory.stream().flatMap(Order::getDishes);
        return sortByFrequence(allDishesOrdered);
    }


    private <T extends Food> Collection<T> sortByFrequence(Stream<T> stream) {
        return stream.collect(Collectors.toMap(f -> f, f -> 1, (a, b) -> a + b))
                .entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .toList();
    }

    public void addDish(Dish newDish) {
        menu.put(newDish.getName(), newDish);
    }

    public void removeDish(String dishName) {
        menu.remove(dishName);
    }

    public void addMeal(Collection<Dish> dishes, String name) throws MealCreationException {
        Meal newMeal = new Meal(dishes, name, this);
        meals.put(newMeal.hashCode(), newMeal);
    }

    public void changeMealOfTheWeek(String name) {
        meals.values().stream().forEach(m -> m.setMealOfTheWeek(false));
        Meal meal = meals.values().stream().filter(m -> m.getName().equals(name)).findFirst().get();
        meal.setMealOfTheWeek(true);
    }

    public String getMealOfTheWeek() {
        return meals.values().stream().filter(Meal::getMealOfTheWeek).findFirst().get().getName();
    }

    public void removeMeal(Meal meal) {
        meals.remove(meal.hashCode());
    }

    public Double getProfit() {
        return orderHistory.stream().mapToDouble(Order::getProfit).sum();
    }

//    public void addFidelityCard(FidelityCard fidelityCard) {
//        this.fidelityCards.putIfAbsent(fidelityCard.getCustomer(), fidelityCard);
//    }

    public void removeFidelityCard(Customer customer) {
        this.fidelityCards.remove(customer);
    }

    public Double getGenericDiscountFactor() {
        return genericDiscountFactor;
    }

    public void setGenericDiscountFactor(Double genericDiscountFactor) {
        this.genericDiscountFactor = genericDiscountFactor;
    }

    public Double getSpecialDiscountFactor() {
        return specialDiscountFactor;
    }

    public void setSpecialDiscountFactor(Double specialDiscountFactor) {
        this.specialDiscountFactor = specialDiscountFactor;
    }

    public Map<Integer, Meal> getMeals() {
        return meals;
    }


    public static void main(String[] args) throws MealCreationException {
        Dish dish1 = new Dish("dish1", 10.0, DishType.Starter, true, false);
        Dish dish2 = new Dish("dish2", 20.0, DishType.MainDish, false, true);
        Dish dish3 = new Dish("dish3", 30.0, DishType.Dessert, true, true);
        Restaurant restaurant = new Restaurant();
        restaurant.addMeal(List.of(dish1, dish2), "meal1");
        restaurant.addMeal(List.of(dish1, dish2, dish3), "meal2");
        //print all meals of this restaurant
        restaurant.getMeals().values().stream().forEach(meal -> System.out.println(meal.getName()));
        // make meal 2 the meal of the week
        restaurant.changeMealOfTheWeek("meal2");
        // print the name of the meal of the week
        System.out.println("The meal of the week is: ");
        System.out.println(restaurant.getMealOfTheWeek());

        //get price of both meals
        System.out.println("Price of meal1: " + restaurant.getMeals().get(restaurant.getMeals().values().stream().filter(m -> m.getName().equals("meal1")).findFirst().get().hashCode()).getPrice());
        System.out.println("Price of meal2: (meal of the week) " + restaurant.getMeals().get(restaurant.getMeals().values().stream().filter(m -> m.getName().equals("meal2")).findFirst().get().hashCode()).getPrice());
    }
}
