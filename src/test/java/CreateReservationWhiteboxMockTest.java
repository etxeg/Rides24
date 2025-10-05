import static org.junit.Assert.*;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Car;
import domain.Driver;
import domain.Ride;
import domain.Traveler;

public class CreateReservationWhiteboxMockTest {

	static DataAccess sut;
	protected MockedStatic<Persistence> persistenceMock;
	@Mock
	protected EntityManagerFactory entityManagerFactory;
	@Mock
	protected EntityManager db;
	@Mock
	protected EntityTransaction et;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	
		persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() ->
		Persistence.createEntityManagerFactory(Mockito.any())).thenReturn(entityManagerFactory);
		Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
		sut=new DataAccess(db);
	 }
	@After
	public void tearDown() {
		persistenceMock.close();
	 }

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
        ride.setRideNumber(10);
        
        traveler.addMoney(100);

        boolean existTraveler = false;

        try {
            // configure the state of the system (create object in the database)
        	Mockito.when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        	Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
        	
        	//	invoke System Under Test (sut)  
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
        	Mockito.when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        	Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

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
        	//Mockito.when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        	Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
        	
            sut.open();
            boolean erreserbaEginDu = sut.createReservation(null, ride, seatKop); // traveler is not initialized
            sut.close();

            assertTrue(!erreserbaEginDu);
        } catch (Exception e) {
            assertTrue(true);
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
        	Mockito.when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        	//Mockito.when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
        	
            sut.open();
            boolean erreserbaEginDu = sut.createReservation(traveler, null, seatKop); // traveler is not initialized
            sut.close();

            assertTrue(!erreserbaEginDu);
        } catch (Exception e) {
        	fail();
        }
    }
}
