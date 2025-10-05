import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertThrows;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.*;
import testOperations.TestDataAccess;

public class CreateReservationBlackboxTest {

	// sut: system under test
    static DataAccess sut = new DataAccess();

    // additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    @Test
    public void testCase1_successfulReservation() throws Exception {
        // setup traveler and ride
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 2, "driver@gmail.com");

        try {
            testDA.open();
            testDA.addTravelerWithMoney(traveler, 100);
            testDA.close();

            sut.open();
            boolean result = sut.createReservation(traveler, ride, 2);
            sut.close();

            assertTrue(result);
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testCase2_notEnoughMoney() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 2, "driver@gmail.com");

        try {
            testDA.open();
            testDA.addTravelerWithMoney(traveler, 10); // not enough money
            testDA.close();

            sut.open();
            boolean result = sut.createReservation(traveler, ride, 2);
            sut.close();

            assertTrue(!result);
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testCase3_tooManyPlaces() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 3, "driver@gmail.com"); // 3 seats

        try {
            testDA.open();
            testDA.addTravelerWithMoney(traveler, 100);
            testDA.close();

            sut.open();
            boolean result = sut.createReservation(traveler, ride, 5); // 5 > 3
            sut.close();

            assertTrue(!result);
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testCase4_nullTraveler() throws Exception {
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 2, "driver@gmail.com");

        try {
            testDA.open();
            //testDA.addTravelerWithMoney(traveler, 100);
            testDA.close();

            sut.open();
            boolean result = sut.createReservation(null, ride, 5); // 5 > 3
            sut.close();

            assertTrue(!result);
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testCase5_nullRide() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");

        try {
            testDA.open();
            testDA.addTravelerWithMoney(traveler, 100);
            testDA.close();

            sut.open();
            boolean result = sut.createReservation(traveler, null, 5); // 5 > 3
            sut.close();

            assertTrue(!result);
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    @Test
    public void testCase6_zeroPlaces() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 2, "driver@gmail.com");

        try {
            testDA.open();
            testDA.addTravelerWithMoney(traveler, 100);
            testDA.close();

            sut.open();
            boolean result = sut.createReservation(traveler, ride, 0);
            sut.close();

            assertTrue(!result);
        } catch (Exception e) {
            fail("Exception not expected: " + e.getMessage());
        }
    }

    // helper method to create a ride quickly
    private Ride createRide(String from, String to, int year, int month, int seats, String driverEmail) {
        Driver driver = new Driver(driverEmail, "Driver", "123");
        Car car = new Car("KTR3427", driver, 5, "Audi");
        LocalDate localDate = LocalDate.of(year, month, 23);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new Ride(from, to, date, seats, driver, car);
    }
}
