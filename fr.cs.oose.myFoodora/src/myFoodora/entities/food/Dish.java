package myFoodora.entities.food;

import myFoodora.enums.DishType;

public class Dish extends Food{
    private DishType category;
    private Double price;


    public Dish(String name, double price, DishType category, boolean vegetarian, boolean glutenFree) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.vegetarian = vegetarian;
        this.glutenFree = glutenFree;
    }
    public Dish(String name, double price, DishType category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.vegetarian = false;
        this.glutenFree = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public DishType getCategory() {
        return category;
    }

    public void setCategory(DishType category) {
        this.category = category;
    }
}
