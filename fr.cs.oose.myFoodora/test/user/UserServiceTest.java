package tests.user;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import myFoodora.MyFoodora;
import myFoodora.entities.Location;
import myFoodora.entities.user.Manager;
import myFoodora.entities.user.Restaurant;
import myFoodora.entities.user.User;
import myFoodora.enums.PermissionType;
import myFoodora.exceptions.UserRegistrationException;
import myFoodora.services.UserBuilder;

class UserServiceTest {

	@Test
	void registerUser() {
		MyFoodora app = MyFoodora.getInstance();
		
		try {
			app.managerService.registerUser(
				UserBuilder.buildUserOfType(Manager.class)
					.addName("Mario")
					.addSurname("Rossi")
					.addCredential("tizio", "123")
					.getResult()
			);
		} catch (UserRegistrationException e) {
			System.out.println(e.message);
		}
		
		User m = app.managerService.getUserById(0);
		
		assertTrue(m != null);
		assertEquals("Mario", m.getName());
		assertEquals(PermissionType.Manager, m.getCredential().getPermission());
		assertEquals("tizio", m.getCredential().getUsername());
		assertEquals("123", m.getCredential().getPassword());
		assertTrue(m instanceof Manager);
	}
	
	@Test
	public void getBestRestaurant() {
		MyFoodora app = MyFoodora.getInstance();
		Location l = new Location(12.0, 10.0);
		
		try {
			app.restaurantService.registerUser(
				UserBuilder.buildUserOfType(Restaurant.class)
					.addCredential("Vivalapizza", "456")
					.addGenericDiscountFactor(15.0)
					.addLocation(l)
					.addSpecialDiscountFactor(50.0)
					.addName("Da Pino")
					.getResult()
			);
			app.restaurantService.registerUser(
					UserBuilder.buildUserOfType(Restaurant.class)
						.addCredential("KetchupOnTop", "456")
						.addGenericDiscountFactor(15.0)
						.addLocation(l)
						.addSpecialDiscountFactor(50.0)
						.addName("Da Pino")
						.getResult()
				);
		} catch (UserRegistrationException e) {
			System.out.println(e.message);
		}
		
		//assertEquals(2, app.userService.getUsersOfType(Restaurant.class).count());
	}

}
