package tests;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import myFoodora.MyFoodora;
import myFoodora.entities.user.Manager;
import myFoodora.entities.user.User;
import myFoodora.enums.UserType;
import myFoodora.exceptions.UserRegistrationException;
import myFoodora.services.UserBuilder;

class UserServiceTest {

	@Test
	void registerUser() {
		MyFoodora app = MyFoodora.getInstance();
		
		try {
			app.userService.registerUser(
				UserBuilder.buildUserOfType(UserType.Manager)
					.addName("Mario")
					.addSurname("Rossi")
					.addCredential("tizio", "123")
					.getResult()
			);
		} catch (UserRegistrationException e) {
			System.out.println(e.message);
		}
		
		User m = app.userService.getUserById(0);
		
		assertTrue(m != null);
		assertEquals("Mario", m.getName());
		assertEquals(UserType.Manager, m.getCredential().getPermission());
		assertEquals("tizio", m.getCredential().getUsername());
		assertEquals("123", m.getCredential().getPassword());
		assertTrue(m instanceof Manager);
	}

}
