package myFoodora.entities.food;

import myFoodora.enums.CategoryEnum;

public class Dish {
    private String name;
    private double price;
    private CategoryEnum category;
    private boolean vegetarian;
    private boolean glutenFree;

    public Dish(String name, double price, CategoryEnum category, boolean vegetarian, boolean glutenFree) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.vegetarian = vegetarian;
        this.glutenFree = glutenFree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CategoryEnum getCategory() {
        return category;
    }

    public void setCategory(CategoryEnum category) {
        this.category = category;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public boolean isGlutenFree() {
        return glutenFree;
    }

    public void setGlutenFree(boolean glutenFree) {
        this.glutenFree = glutenFree;
    }
}
