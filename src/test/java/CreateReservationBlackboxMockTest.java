import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.ZoneId;
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
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Car;
import domain.Driver;
import domain.Ride;
import domain.Traveler;

public class CreateReservationBlackboxMockTest {

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

        persistenceMock = org.mockito.Mockito.mockStatic(Persistence.class);
        persistenceMock.when(() -> Persistence.createEntityManagerFactory(any()))
                       .thenReturn(entityManagerFactory);

        when(entityManagerFactory.createEntityManager()).thenReturn(db);
        when(db.getTransaction()).thenReturn(et);

        sut = new DataAccess(db);
    }

    @After
    public void tearDown() {
        persistenceMock.close();
    }

    private Ride createRide(String from, String to, int year, int month, int seats, String driverEmail) {
        Driver driver = new Driver(driverEmail, "Driver", "123");
        Car car = new Car("KTR3427", driver, 5, "Audi");
        LocalDate localDate = LocalDate.of(year, month, 23);
        Date date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        return new Ride(from, to, date, seats, driver, car);
    }

    @Test
    public void testCase1_successfulReservation() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");
        traveler.addMoney(100);
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 2, "driver@gmail.com");

        when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

        sut.open();
        boolean result = sut.createReservation(traveler, ride, 2);
        sut.close();

        assertTrue(result);
    }

    @Test
    public void testCase2_notEnoughMoney() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");
        traveler.addMoney(10); // not enough
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 2, "driver@gmail.com");

        when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

        sut.open();
        boolean result = sut.createReservation(traveler, ride, 2);
        sut.close();

        assertFalse(result);
    }

    @Test
    public void testCase3_tooManyPlaces() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");
        traveler.addMoney(100);
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 3, "driver@gmail.com"); // 3 seats

        when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

        sut.open();
        boolean result = sut.createReservation(traveler, ride, 5); // 5 > 3
        sut.close();

        assertFalse(result);
    }

    @Test
    public void testCase4_nullTraveler() throws Exception {
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 2, "driver@gmail.com");

        //when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

        sut.open();
        boolean result = sut.createReservation(null, ride, 1);
        sut.close();

        assertFalse(result);
    }

    @Test
    public void testCase5_nullRide() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");

        when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        //when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);
        
        sut.open();
        boolean result = sut.createReservation(traveler, null, 1);
        sut.close();

        assertFalse(result);
    }

    @Test
    public void testCase6_zeroPlaces() throws Exception {
        Traveler traveler = new Traveler("traveler@gmail.com", "Traveler", "123");
        traveler.addMoney(100);
        Ride ride = createRide("Donostia", "Gasteiz", 2025, 10, 2, "driver@gmail.com");

        when(db.find(Traveler.class, traveler.getEmail())).thenReturn(traveler);
        when(db.find(Ride.class, ride.getRideNumber())).thenReturn(ride);

        sut.open();
        boolean result = sut.createReservation(traveler, ride, 0);
        sut.close();

        assertFalse(result);
    }
}
