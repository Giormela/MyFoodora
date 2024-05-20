package myFoodora.entities.food;

import myFoodora.enums.DishType;

public class Dish extends Food{
    private String name;
    private double price;
    private DishType category;


    public Dish(String name, double price, DishType category, boolean vegetarian, boolean glutenFree) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.vegeterian = vegetarian;
        this.glutenFree = glutenFree;
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

    public boolean isVegetarian() {
        return this.vegeterian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegeterian = vegetarian;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }
}
