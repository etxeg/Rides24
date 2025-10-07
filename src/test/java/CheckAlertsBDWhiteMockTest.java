import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import dataAccess.DataAccess;
import domain.Alert;
import domain.Car;
import domain.Driver;
import domain.Ride;
import domain.Traveler;

public class checkAlertsBDWhiteMockTest {

static DataAccess sut;
	
	protected MockedStatic <Persistence> persistenceMock;

	@Mock
	protected  EntityManagerFactory entityManagerFactory;
	@Mock
	protected  EntityManager db;
	@Mock
    protected  EntityTransaction  et;
	@Mock
    protected TypedQuery<Ride> query;
	

	@Before
    public  void init() {
        MockitoAnnotations.openMocks(this);
        persistenceMock = Mockito.mockStatic(Persistence.class);
		persistenceMock.when(() -> Persistence.createEntityManagerFactory(Mockito.any()))
        .thenReturn(entityManagerFactory);
        
        Mockito.doReturn(db).when(entityManagerFactory).createEntityManager();
		Mockito.doReturn(et).when(db).getTransaction();
	    sut=new DataAccess(db);


		
    }
	@After
    public  void tearDown() {
		persistenceMock.close();


    }
	
	
	Driver driver;
	Traveler traveler;
	Alert alerta;
	Ride ride;
	
	
	@Test
	//sut.checkAlerts:  The Traveler ("Pedro Fernandez", "traveler1@gmail.com") HAS no alerts. 
	public void test1() {
		String travelerEmail="traveler1@gmail.com";
		String travelerName="Pedro Fernandez";
		
		try {
			
			Traveler traveler = new Traveler(travelerEmail, travelerName, "123");
			//configure the state through mocks
			
			
			//invoke System Under Test (sut)  
			sut.open();
			List<Ride> erantzuna=sut.checkAlerts(traveler);
			sut.close();
			if(erantzuna.isEmpty()) {
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	//sut.checkAlerts:  The Traveler ("Pedro Fernandez", "traveler1@gmail.com") HAS one alert "from" "to" in that "date", but no Ride "from" "to" in that "date". 
	public void test2() {
		String travelerEmail="traveler1@gmail.com";
		String travelerName="Pedro Fernandez";
		String from="Donosti";
		String to="Bilbo";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("05/11/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		List<Ride> lista= new ArrayList<>();
		try {
			
			Traveler traveler = new Traveler(travelerEmail, travelerName, "123");
			traveler.addAlert(from, to, rideDate);
			//configure the state through mocks
			Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(query);
			Mockito.when(query.getResultList()).thenReturn(lista);
			
			//invoke System Under Test (sut)  
			sut.open();
			List<Ride> erantzuna=sut.checkAlerts(traveler);
			sut.close();
			if(erantzuna.isEmpty()) {
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	//sut.checkAlerts:  The Traveler ("Pedro Fernandez", "traveler1@gmail.com") HAS one alert "from" "to" in that "date" and there is one Ride "from" "to" in that "date". 
	public void test3() {
		String travelerEmail="traveler1@gmail.com";
		String travelerName="Pedro Fernandez";
		String from="Donosti";
		String to="Bilbo";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		try {
			rideDate = sdf.parse("12/11/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		List<Ride> lista= new ArrayList<>();
		try {
			Traveler traveler = new Traveler(travelerEmail, travelerName, "123");
			traveler.addAlert(from, to, rideDate);
			Car car1 = new Car("AM", driver, 4, "Toyota");
			Ride ride = new Ride(1, from, to, rideDate, 25, null, car1);
			lista.add(ride);
			//configure the state through mocks
			Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(query);
			Mockito.when(query.getResultList()).thenReturn(lista);
			
			//invoke System Under Test (sut)  
			sut.open();
			sut.createAlert(traveler, from, to, rideDate);
			List<Ride> erantzuna=sut.checkAlerts(traveler);
			sut.close();
			if(!erantzuna.isEmpty()) {
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			e.printStackTrace();
			fail();
		}
	}

}
