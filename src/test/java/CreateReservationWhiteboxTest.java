import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Date;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.*;
import testOperations.TestDataAccess;

public class CreateReservationWhiteboxTest {

    // sut: system under test
    static DataAccess sut = new DataAccess();

    // additional operations needed to execute the test
    static TestDataAccess testDA = new TestDataAccess();

    private Traveler traveler;
    private Ride ride;

    @Test
    // sut.createReservation: The Traveler("Traveler", "traveler@gmail.com") - Dena ongi
    public void test1() {
        String email = "traveler@gmail.com";
        String name = "Traveler";
        String password = "123";

        Traveler traveler = new Traveler(email, name, password);

        String rideFrom = "Donostia";
        String rideTo = "Gasteiz";
        Date date = new Date();
        int seatKop = 2;

        String driverEmail = "driver@gmail.com";
        Driver driver = new Driver(driverEmail, "Driver", password);
        Car car = new Car("KTR3427", driver, 5, "Audi");
        Ride ride = new Ride(rideFrom, rideTo, date, seatKop, driver, car);

        boolean existTraveler = false;

        try {
            // configure the state of the system (create object in the database)
            testDA.open();
            existTraveler = testDA.existTraveler(email);
            testDA.addTravelerWithMoney(traveler, 100);
            testDA.close();

            sut.open();
            boolean erreserbaEginDu = sut.createReservation(traveler, ride, seatKop);
            sut.close();

            assertTrue(erreserbaEginDu);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    // sut.createReservation: The Traveler("Traveler", "traveler@gmail.com") - Diru nahikoa ez
    public void test2() {
        String email = "traveler@gmail.com";
        String name = "Traveler";
        String password = "123";

        Traveler traveler = new Traveler(email, name, password);

        String rideFrom = "Donostia";
        String rideTo = "Gasteiz";
        Date date = new Date();
        int seatKop = 2;

        String driverEmail = "driver@gmail.com";
        Driver driver = new Driver(driverEmail, "Driver", password);
        Car car = new Car("KTR3427", driver, 5, "Audi");
        Ride ride = new Ride(rideFrom, rideTo, date, seatKop, driver, car);

        boolean existTraveler = false;

        try {
            // configure the state of the system (create object in the database)
            testDA.open();
            existTraveler = testDA.existTraveler(email);
            testDA.addTravelerWithMoney(traveler, 0);
            testDA.close();

            sut.open();
            boolean erreserbaEginDu = sut.createReservation(traveler, ride, seatKop);
            sut.close();

            assertTrue(erreserbaEginDu);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    // sut.createReservation: Bidaiaririk ez badago
    public void test3() {
        String password = "123";

        String rideFrom = "Donostia";
        String rideTo = "Gasteiz";
        Date date = new Date();
        int seatKop = 2;

        String driverEmail = "driver@gmail.com";
        Driver driver = new Driver(driverEmail, "Driver", password);
        Car car = new Car("KTR3427", driver, 5, "Audi");
        Ride ride = new Ride(rideFrom, rideTo, date, seatKop, driver, car);

        try {
            sut.open();
            boolean erreserbaEginDu = sut.createReservation(null, ride, seatKop); // traveler is not initialized
            sut.close();

            assertTrue(!erreserbaEginDu);
        } catch (Exception e) {
        	fail();
        }
    }
    
    @Test
    // sut.createReservation: Bidaiarik ez badago
    public void test4() {
    	 String email = "traveler@gmail.com";
         String name = "Traveler";
         String password = "123";

        Traveler traveler = new Traveler(email, name, password);

        String rideFrom = "Donostia";
        String rideTo = "Gasteiz";
        Date date = new Date();
        int seatKop = 2;

        String driverEmail = "driver@gmail.com";
        Driver driver = new Driver(driverEmail, "Driver", password);
        Car car = new Car("KTR3427", driver, 5, "Audi");
        //Ride ride = new Ride(rideFrom, rideTo, date, seatKop, driver, car);

        try {
            sut.open();
            boolean erreserbaEginDu = sut.createReservation(traveler, null, seatKop); // traveler is not initialized
            sut.close();

            assertTrue(!erreserbaEginDu);
        } catch (Exception e) {
        	fail();
        }
    }
    
    
    
}
