package myFoodora;

public class Launcher {
    public static void main(String[] args) {
    	MyFoodora app = MyFoodora.getInstance();
    	app.run();
    }
    
    
    
    
    
    
    
    
    
//        boolean running = true;
//        while (running) {
//            System.out.println("Press 1 to create a dish, 2 to print all dishes, 3 to exit:");
//            int choice = scanner.nextInt();
//            scanner.nextLine(); // consume newline left-over
//            switch (choice) {
//                case 1:
//                    createDish();
//                    break;
//                case 2:
//                    printDishes();
//                    break;
//                case 3:
//                    running = false;
//                    break;
//                default:
//                    System.out.println("Invalid input. Please try again.");
//            }
//        }
    }

//    private static void createDish() {
//        System.out.println("Enter dish name:");
//        String name = scanner.nextLine();
//
//        System.out.println("Enter dish price:");
//        double price = scanner.nextDouble();
//        scanner.nextLine(); // consume newline left-over
//
//        System.out.println("Enter category (1 for STARTER, 2 for MAIN_COURSE, 3 for DESSERT):");
//        int categoryChoice = scanner.nextInt();
//        scanner.nextLine(); // consume newline left-over
//        DishType category = DishType.values()[categoryChoice - 1];
//
//        System.out.println("Is the dish vegetarian? (true/false)");
//        boolean vegetarian = scanner.nextBoolean();
//
//        System.out.println("Is the dish gluten-free? (true/false)");
//        boolean glutenFree = scanner.nextBoolean();
//
//        Dish dish = new Dish(name, price, category, vegetarian, glutenFree);
//        dishes.add(dish);
//        System.out.println("Dish added successfully!");
//    }
//
//    private static void printDishes() {
//        if (dishes.isEmpty()) {
//            System.out.println("No dishes available.");
//        } else {
//            System.out.println("Available Dishes:");
//            for (Dish dish : dishes) {
//                System.out.println("Name: " + dish.getName() + ", Price: " + dish.getPrice() +
//                        ", Category: " + dish.getCategory() +
//                        ", Vegetarian: " + dish.isVegetarian() +
//                        ", Gluten-Free: " + dish.isGlutenFree());
//            }
//        }
//    }
//}

