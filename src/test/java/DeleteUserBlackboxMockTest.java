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
import domain.Admin;
import domain.Car;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import domain.User;

public class DeleteUserBlackboxMockTest {

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

    @Test
    public void testCase1_deleteTraveler() {
        Traveler traveler = new Traveler("trav@gmail.com", "Traveler", "123");
        when(db.find(User.class, traveler)).thenReturn(traveler);
        sut.open();
        boolean result = sut.deleteUser(traveler);
        sut.close();
        assertTrue(result);
    }

    @Test
    public void testCase2_deleteDriver() {
        Driver driver = new Driver("driver@gmail.com", "Driver", "123");
        when(db.find(User.class, driver)).thenReturn(driver);
        sut.open();
        boolean result = sut.deleteUser(driver);
        sut.close();
        assertTrue(result);
    }

    @Test
    public void testCase4_invalidUser() {
        User u = new User("u@gmail.com", "User", "123");
        when(db.find(User.class, u)).thenReturn(u);
        sut.open();
        boolean result = sut.deleteUser(u);
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

