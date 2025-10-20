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

public class CheckAlertsBlackMockTest {
//aldaketa
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
	//sut.checkAlerts:  null balioa jasotzen du. 
	public void test1() {
		try {
			
			Traveler traveler=null;
			
			//invoke System Under Test (sut)  
			sut.open();
			List<Ride> erantzuna=sut.checkAlerts(traveler);
			sut.close();
			fail();
			
		}catch(NullPointerException e) {
			assertTrue(true);
		}
	}
	
	@Test
	//sut.checkAlerts:  The Traveler ("Aitor Fernandez", "traveler1@gmail.com") is not in the DB. 
	
	//Metodo onek errore bat aurkitzen du, datu basean gordeta ez dagoen traveler bat metodoan sartzean traveleraren alerten Ride lista edo lista hutsa bueltatzen du null bueltatu ordez
	public void test2() {
		String travelerEmail="traveler1@gmail.com";
		String travelerName="Aitor Fernandez";
		
		String from="Donosti";
		String to="Bilbo";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		
		try {
			rideDate = sdf.parse("07/10/2025");
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
			
			if(erantzuna==null) {
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
	//sut.checkAlerts:  The Traveler ("Aitor Fernandez", "traveler1@gmail.com") HAS one alert "from" "to" in that "date" and there is one Ride "from" "to" in that "date".
	public void test3() {
		String travelerEmail="traveler1@gmail.com";
		String travelerName="Aitor Fernandez";
		String from="Donosti";
		String to="Bilbo";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;
		
		try {
			rideDate = sdf.parse("02/12/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		List<Ride> lista= new ArrayList<>();
		try {
			traveler=new Traveler(travelerEmail, travelerName, "123");
			Car car1 = new Car("MJ", null, 4, "marka1");
			Ride ride1=new Ride(1, from, to, rideDate, 25, null, car1);
			lista.add(ride1);
			//configure the state through mocks 
			Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(query);
			Mockito.when(query.getResultList()).thenReturn(lista);
			
			//invoke System Under Test (sut)  
			sut.open();
			traveler.addAlert(from, to, rideDate);
			List<Ride> erantzuna=sut.checkAlerts(traveler);
			sut.close();
			if(!erantzuna.isEmpty()) {
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}
	}
	
	@Test
	//sut.checkAlerts:  The Traveler ("Aitor Fernandez", "traveler1@gmail.com") HAS one alert "from" "to" in that "date", but no Ride "from" "to" in that "date". 
	public void test4() {
		String travelerEmail="traveler1@gmail.com";
		String travelerName="Aitor Fernandez";
		String from="Barcelona";
		String to="Madrid";
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date rideDate=null;;
		try {
			rideDate = sdf.parse("15/11/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		List<Ride> lista= new ArrayList<>();
		try {
			traveler=new Traveler(travelerEmail, travelerName, "123");
			Car car1 = new Car("MJ", null, 4, "marka1");
			Ride ride1=new Ride(1, from, to, rideDate, 25, null, car1);
			
			//configure the state through mocks 
			Mockito.when(db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",Ride.class)).thenReturn(query);
			Mockito.when(query.getResultList()).thenReturn(lista);
			
			//invoke System Under Test (sut)  
			sut.open();
			traveler.addAlert(from, to, rideDate);
			List<Ride> erantzuna=sut.checkAlerts(traveler);
			sut.close();
			if(erantzuna.isEmpty()) {
				assertTrue(true);
			}else {
				fail();
			}
		}catch(Exception e) {
			fail();
		}
	}

}

