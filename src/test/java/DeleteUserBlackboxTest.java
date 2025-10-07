import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.*;
import testOperations.TestDataAccess;

public class DeleteUserBlackboxTest {

    static DataAccess sut = new DataAccess();
    static TestDataAccess testDA = new TestDataAccess();

    @Test
    public void testCase1_deleteTraveler() {
        Traveler traveler = new Traveler("trav@gmail.com", "Traveler", "123");
        testDA.open();
        testDA.existTraveler("trav@gmail.com");
        testDA.close();
        sut.open();
        boolean result = sut.deleteUser(traveler);
        sut.close();
        assertTrue(result);
        testDA.open();
        assertFalse(testDA.existTraveler(traveler.getEmail()));
        testDA.close();
    }

    @Test
    public void testCase2_deleteDriver() {
        Driver driver = new Driver("driver@gmail.com", "Driver", "123");
        testDA.open();
        testDA.existDriver("driver@gmail.com");
        testDA.close();
        sut.open();
        boolean result = sut.deleteUser(driver);
        sut.close();
        assertTrue(result);
        testDA.open();
        assertFalse(testDA.existDriver(driver.getEmail()));
        testDA.close();
    }

    @Test
    public void testCase4_invalidUser() {
        User user = new User("u@gmail.com", "User", "123");
        sut.open();
        boolean result = sut.deleteUser(user);
        sut.close();
        assertFalse(result);
    }

    @Test
    public void testCase5_nullUser() {
        sut.open();
        boolean result = sut.deleteUser(null);
        sut.close();
        assertFalse(result);
    }
}
