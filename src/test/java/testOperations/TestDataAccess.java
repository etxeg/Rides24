package testOperations;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import configuration.ConfigXML;
import domain.Car;
import domain.Driver;
import domain.Reservation;
import domain.Ride;
import domain.Traveler;


public class TestDataAccess {
	protected  EntityManager  db;
	protected  EntityManagerFactory emf;

	ConfigXML  c=ConfigXML.getInstance();


	public TestDataAccess()  {
		
		System.out.println("TestDataAccess created");

		//open();
		
	}

	
	public void open(){
		

		String fileName=c.getDbFilename();
		
		if (c.isDatabaseLocal()) {
			  emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
			  db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			  properties.put("javax.persistence.jdbc.user", c.getUser());
			  properties.put("javax.persistence.jdbc.password", c.getPassword());

			  emf = Persistence.createEntityManagerFactory("objectdb://"+c.getDatabaseNode()+":"+c.getDatabasePort()+"/"+fileName, properties);

			  db = emf.createEntityManager();
    	   }
		System.out.println("TestDataAccess opened");

		
	}
	public void close(){
		db.close();
		System.out.println("TestDataAccess closed");
	}

	public boolean removeDriver(String driverEmail) {
		System.out.println(">> TestDataAccess: removeRide");
		Driver d = db.find(Driver.class, driverEmail);
		if (d!=null) {
			db.getTransaction().begin();
			db.remove(d);
			db.getTransaction().commit();
			return true;
		} else 
		return false;
    }
	public Driver createDriver(String email, String name) {
		System.out.println(">> TestDataAccess: addDriver");
		Driver driver=null;
			db.getTransaction().begin();
			try {
			    driver=new Driver(name,email,"123");
				db.persist(driver);
				db.getTransaction().commit();
			}
			catch (Exception e){
				e.printStackTrace();
			}
			return driver;
    }
	public boolean existDriver(String email) {
		 return  db.find(Driver.class, email)!=null;
		 

	}
		
		public Driver addDriverWithRide(String email, String name, String from, String to,  Date date, float price, Car pCar) {
			System.out.println(">> TestDataAccess: addDriverWithRide");
				Driver driver=null;
				db.getTransaction().begin();
				try {
					 driver = db.find(Driver.class, email);
					if (driver==null)
						driver=new Driver(name,email,"123");
				    driver.addRide(from, to, date, price, pCar);
					db.getTransaction().commit();
					return driver;
					
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return null;
	    }
		
		
		public boolean existRide(String email, String from, String to, Date date) {
			System.out.println(">> TestDataAccess: existRide");
			Driver d = db.find(Driver.class, email);
			if (d!=null) {
				return d.doesRideExists(from, to, date);
			} else 
			return false;
		}
		public Ride removeRide(String email, String from, String to, Date date ) {
			System.out.println(">> TestDataAccess: removeRide");
			Driver d = db.find(Driver.class, email);
			if (d!=null) {
				db.getTransaction().begin();
				Ride r= d.removeRide(from, to, date);
				db.getTransaction().commit();
				return r;

			} else 
			return null;

		}
		
		public boolean existTraveler(String email) { 

		 	return db.find(Traveler.class, email)!=null; 

		} 

		 

		public Traveler addTravelerWithMoney(Traveler traveler, float i) { 

			System.out.println(">> TestDataAccess: addTravelerWithMoney"); 

			Traveler traveler2=null; 

			db.getTransaction().begin(); 

			try { 

				traveler2 = db.find(Traveler.class,	traveler.getEmail()); 

				if (traveler2==null) {
					traveler2=new Traveler(traveler.getName(),traveler.getEmail(),traveler.getPassword()); 

					traveler2.addMoney(i); 

					db.getTransaction().commit();
				}
				
				return traveler2; 

			} catch (Exception e){ 

				e.printStackTrace(); 

			} 

			return null; 

		}

		
		public Traveler createTraveler(String email, String name) {
			System.out.println(">> TestDataAccess: addDriver");
			Traveler traveler=null;
				db.getTransaction().begin();
				try {
				    traveler=new Traveler(email,name,"123");
					db.persist(traveler);
					db.getTransaction().commit();
				}
				catch (Exception e){
					e.printStackTrace();
				}
				return traveler;
	    }
		
		public boolean removeTraveler(String travelerEmail) {
			System.out.println(">> TestDataAccess: removeTraveler");
			Traveler d = db.find(Traveler.class, travelerEmail);
			if (d!=null) {
				db.getTransaction().begin();
				db.remove(d);
				db.getTransaction().commit();
				return true;
			} else 
			return false;
	    }


		
}


