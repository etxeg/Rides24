
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import javax.persistence.*;
import org.junit.*;
import org.mockito.*;

import dataAccess.DataAccess;
import domain.*;

public class DeleteUserWhiteboxMockTest {

    static DataAccess sut;
    protected MockedStatic<Persistence> persistenceMock;
    @Mock protected EntityManagerFactory entityManagerFactory;
    @Mock protected EntityManager db;
    @Mock protected EntityTransaction et;

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

    @Test
    public void testCase1_travelerWithReservations() {
        Traveler traveler = new Traveler("t@gmail.com", "Traveler", "123");
        traveler.getReservations().add(new Reservation(traveler, null, null, 0));
        when(db.find(User.class, traveler)).thenReturn(traveler);
        sut.open();
        boolean result = sut.deleteUser(traveler);
        sut.close();
        assertTrue(result);
        verify(db).remove(traveler);
    }

    @Test
    public void testCase2_driverWithRides() {
        Driver driver = new Driver("d@gmail.com", "Driver", "123");
        driver.getRides().add(new Ride());
        when(db.find(User.class, driver)).thenReturn(driver);
        sut.open();
        boolean result = sut.deleteUser(driver);
        sut.close();
        assertTrue(result);
        verify(db).remove(driver);
    }

    @Test
    public void testCase3_admin() {
        Admin admin = new Admin("a@gmail.com", "Admin", "123");
        when(db.find(User.class, admin)).thenReturn(admin);
        sut.open();
        boolean result = sut.deleteUser(admin);
        sut.close();
        assertTrue(result);
        verify(db, never()).remove(admin);
    }

    @Test
    public void testCase4_invalidUser() {
        User user = new User("u@gmail.com", "User", "123");
        when(db.find(User.class, user)).thenReturn(user);
        sut.open();
        boolean result = sut.deleteUser(user);
        sut.close();
        assertFalse(result);
    }

    @Test
    public void testCase5_exception() {
        Traveler traveler = new Traveler("err@gmail.com", "Err", "123");
        when(db.find(User.class, traveler)).thenThrow(RuntimeException.class);
        sut.open();
        boolean result = sut.deleteUser(traveler);
        sut.close();
        assertFalse(result);
        verify(et).rollback();
    }
}
