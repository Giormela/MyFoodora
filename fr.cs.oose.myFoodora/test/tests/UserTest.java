package tests;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import myFoodora.entities.Location;
import myFoodora.entities.user.*;
import myFoodora.enums.PermissionType;
import myFoodora.services.UserBuilder;

class UserTest {

	@Test
	void creatingManager() {
		Manager m = UserBuilder.buildUserOfType(Manager.class)
			.addName("Mario")
			.addSurname("Rossi")
			.addCredential("tizio", "123")
			.getResult();
			
		assertEquals("Mario", m.getName());
		assertEquals("Rossi", m.getSurname());
		assertEquals(PermissionType.Manager, m.getCredential().getPermission());
		assertEquals("tizio", m.getCredential().getUsername());
		assertEquals("123", m.getCredential().getPassword());
		assertTrue(m instanceof Manager);
	}
	
	@Test
	void CreatingRestaurant() {
		Location l = new Location(12.0, 10.0);
		Restaurant r = UserBuilder.buildUserOfType(Restaurant.class)
			.addCredential("Vivalapizza", "456")
			.addGenericDiscountFactor(15.0)
			.addLocation(l)
			.addSpecialDiscountFactor(50.0)
			.addName("Da Pino")
			.getResult();
		
		assertEquals("Da Pino", r.getName());
		assertEquals(l, r.getLocation());
		assertEquals(PermissionType.Restaurant, r.getCredential().getPermission());
		assertEquals("Vivalapizza", r.getCredential().getUsername());
		assertEquals("456", r.getCredential().getPassword());
		assertEquals(15.0, r.getGenericDiscountFactor());
		assertEquals(50.0, r.getSpecialDiscountFactor());
		assertTrue(r instanceof Restaurant);
	}
	
	@Test
	void creatingCustomer() {
		Location l = new Location(12.0, 10.0);
		Customer c = UserBuilder.buildUserOfType(Customer.class)
			.addCredential("username", "890")
			.addSurname("Verdi")
			.addPhone("3486496333")
			.addEmail("mike.buongiorno@gmail.com")
			.addGenericDiscountFactor(15.0)
			.addLocation(l)
			.addSpecialDiscountFactor(50.0)
			.addName("Luigi")
			.getResult();
		
		assertEquals("Luigi", c.getName());
		assertEquals("Verdi", c.getSurname());
		assertEquals(l, c.getLocation());
		assertEquals(PermissionType.Customer, c.getCredential().getPermission());
		assertEquals("username", c.getCredential().getUsername());
		assertEquals("890", c.getCredential().getPassword());
		assertEquals("mike.buongiorno@gmail.com", c.getEmail());
		assertEquals("3486496333", c.getPhone());
		assertTrue(c instanceof Customer);
	}
}
