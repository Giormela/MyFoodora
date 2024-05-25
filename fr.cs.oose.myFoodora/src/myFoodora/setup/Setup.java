package myFoodora.setup;

import java.util.stream.Stream;

import myFoodora.MyFoodora;
import myFoodora.entities.Location;
import myFoodora.entities.user.Manager;
import myFoodora.entities.user.Restaurant;
import myFoodora.exceptions.UserRegistrationException;
import myFoodora.services.UserBuilder;

public class Setup {
	public static void setup() {
		MyFoodora app = MyFoodora.getInstance();
		
		Manager m = UserBuilder.buildUserOfType(Manager.class)
				.addName("Giovanni")
				.addSurname("Vitelli")
				.addCredential("ceo", "123456789")
				.getResult();
		
		Location l = new Location(12.0, 10.0);
		Restaurant[] restaurants = new Restaurant[] {
				UserBuilder.buildUserOfType(Restaurant.class)
					.addCredential("aug", "123")
					.addGenericDiscountFactor(15.0)
					.addLocation(l)
					.addSpecialDiscountFactor(50.0)
					.addName("Augusto")
					.getResult(),
				UserBuilder.buildUserOfType(Restaurant.class)
					.addCredential("fra", "123")
					.addGenericDiscountFactor(10.0)
					.addLocation(l)
					.addSpecialDiscountFactor(30.0)
					.addName("Fratelli La Cozza")
					.getResult(),
		};
		
		
		
		
		try {
			app.managerService.registerUser(m);
			for (Restaurant r : restaurants) 
				app.restaurantService.registerUser(r);
			
		} catch (UserRegistrationException e) {
			e.printStackTrace();
		}
		
	}
}
