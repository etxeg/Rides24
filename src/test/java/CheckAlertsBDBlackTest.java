import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import dataAccess.DataAccess;
import domain.Alert;
import domain.Car;
import domain.Driver;
import domain.Ride;
import domain.Traveler;
import testOperations.TestDataAccess;

public class CheckAlertsBDBlackTest {

	//sut:system under test
		 static DataAccess sut=new DataAccess();
		 
		 //additional operations needed to execute the test 
		 static TestDataAccess testDA=new TestDataAccess();

		private Traveler traveler; 
		private Driver driver; 
		
	@Test
	//sut.checkAlerts:  null balioa jasotzen du. 
	public void test1() {
		try {
			//configure the state of the system (create object in the database)
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
		
		String driverEmail="driver1@gmail.com";
		String driverName="Juan Guzman";
		try {
			rideDate = sdf.parse("07/10/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		boolean existDriver=false;
		boolean existTraveler=false;
		try {
			//configure the state of the system (create object in the database)
			Traveler traveler = new Traveler(travelerEmail, travelerName, "123");
			traveler.addAlert(from, to, rideDate);
			
			testDA.open();
			existDriver=testDA.existDriver(driverEmail);
			testDA.createDriver(driverEmail, driverName);
			Car car1 = new Car("AM", driver, 4, "Toyota");
			testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 0, car1);
			testDA.close();
			
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
		}finally{
			//Remove the created objects in the database (cascade removing)   
			testDA.open();
			  if (!existTraveler) 
				  testDA.removeTraveler(travelerEmail);
			  
			  if (existDriver) 
				  testDA.removeRide(driverEmail, from, to, rideDate);
			  else 
				  testDA.removeDriver(driverEmail);
	          testDA.close();
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
		
		String driverEmail="driver1@gmail.com";
		String driverName="Juan Guzman";
		try {
			rideDate = sdf.parse("02/12/2025");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		boolean existTraveler=false;
		boolean existDriver=false;
		try {
			//configure the state of the system (create object in the database)
			testDA.open();
			 existTraveler=testDA.existTraveler(travelerEmail);
			Traveler traveler = testDA.createTraveler(travelerEmail, travelerName);
			
			existDriver=testDA.existDriver(driverEmail);
			testDA.createDriver(driverEmail, driverName);
			Car car1 = new Car("AM", driver, 4, "Toyota");
			testDA.addDriverWithRide(driverEmail, driverName, from, to, rideDate, 0, car1);
			testDA.close();
			
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
		}finally {
			  //Remove the created objects in the database (cascade removing)   
			testDA.open();
			  if (!existTraveler) 
				  testDA.removeTraveler(travelerEmail);
			  
			  if (existDriver) 
				  testDA.removeRide(driverEmail, from, to, rideDate);
			  else 
				  testDA.removeDriver(driverEmail);
	          testDA.close();
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
		
		boolean existTraveler=false;
		try {
			//configure the state of the system (create object in the database)
			testDA.open();
			 existTraveler=testDA.existTraveler(travelerEmail);
			Traveler traveler = testDA.createTraveler(travelerEmail, travelerName);
			testDA.close();
			
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
		}finally {
			  //Remove the created objects in the database (cascade removing)   
			testDA.open();
			  if (!existTraveler) 
				  testDA.removeTraveler(travelerEmail);
	          testDA.close();
	        }
	}

}

