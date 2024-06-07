package myFoodora.tests.utils;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import myFoodora.entities.Date;
import myFoodora.entities.Location;

public class UtilsTest {

    /**
     * Test to ensure that the now() method correctly returns the current date.
     */
    @Test
    public void testDateNow() {
        Date now = Date.now();
        assertEquals(java.util.Calendar.getInstance().get(java.util.Calendar.YEAR), now.getYear());
        assertEquals(java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1, now.getMonth());
        assertEquals(java.util.Calendar.getInstance().get(java.util.Calendar.DAY_OF_MONTH), now.getDay());
    }

    /**
     * Test to ensure that the oneMonthAgo() method correctly calculates the date one month ago.
     */
    @Test
    public void testDateOneMonthAgo() {
        java.util.Calendar calendar = java.util.Calendar.getInstance();
        calendar.add(java.util.Calendar.MONTH, -1); // Manually compute one month ago
        Date oneMonthAgo = Date.oneMonthAgo();
        assertEquals(calendar.get(java.util.Calendar.YEAR), oneMonthAgo.getYear());
        assertEquals(calendar.get(java.util.Calendar.MONTH) + 1, oneMonthAgo.getMonth()); // java.util.Calendar.MONTH is zero-based
    }

    /**
     * Test the comparison between two Date instances.
     */
    @Test
    public void testDateComparison() {
        Date earlier = new Date(2023, 5, 10);
        Date later = new Date(2023, 5, 20);
        assertTrue(earlier.compareTo(later) < 0, "Earlier should be less than later");
        assertEquals(0, earlier.compareTo(new Date(2023, 5, 10)), "Comparing the same date should return 0");
        assertTrue(later.compareTo(earlier) > 0, "Later should be greater than earlier");
    }

    /**
     * Test to ensure the correct functionality of converting an address to coordinates.
     */
    @Test
    public void testLocationConvertFromAddressToCoordinates() {
        Location loc = Location.convertFromAdressToCoordinates("123 Main St");
        assertNotNull(loc);
        assertTrue(loc.getLongitude() >= -180 && loc.getLongitude() <= 180);
        assertTrue(loc.getLatitude() >= -90 && loc.getLatitude() <= 90);
    }

    /**
     * Test to check the distance calculation between two locations.
     */
    @Test
    public void testLocationDistanceFrom() {
        Location loc1 = new Location(0.0, 0.0);
        Location loc2 = new Location(3.0, 4.0);
        assertEquals(5.0, loc1.getDistanceFrom(loc2), 0.001); // Using Pythagoras theorem, expect distance 5
    }
}
