package fr.cs.oose.myFoodora.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import fr.cs.oose.myFoodora.user.*;

class TestUser {

	@Test
	void creatingManager() {
		Manager m = UserBuilder.buildManager()
			.addName("Mario")
			.addSurname("Rossi")
			.addCredential("tizio", "123")
			.getResult();
		
		assertEquals("Mario", m.getName());
		assertEquals("Rossi", m.getSurname());
		assertEquals(UserType.Manager, m.getCredential().getPermission());
		assertEquals("tizio", m.getCredential().getUsername());
		assertEquals("123", m.getCredential().getPassword());
		assertTrue(m instanceof Manager);
	}
	
	@Test
	void CreatingRestaurant() {
		Location l = new Location(12.0, 10.0);
		Restaurant r = UserBuilder.buildRestaurant()
			.addCredential("Vivalapizza", "456")
			.addGenericDiscountFactor(15.0)
			.addLocation(l)
			.addSpecialDiscountFactor(50.0)
			.addName("Da Pino")
			.getResult();
		
		assertEquals("Da Pino", r.getName());
		assertEquals(l, r.getLocation());
		assertEquals(UserType.Restaurant, r.getCredential().getPermission());
		assertEquals("Vivalapizza", r.getCredential().getUsername());
		assertEquals("456", r.getCredential().getPassword());
		assertEquals(15.0, r.getGenericDiscountFactor());
		assertEquals(50.0, r.getSpecialDiscountFactor());
		assertTrue(r instanceof Restaurant);
	}
	
	

}
