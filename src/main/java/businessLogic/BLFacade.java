package businessLogic;

import java.util.Date;
import java.util.List;

//import domain.Booking;
import domain.Ride;
import domain.Traveler;
import domain.User;
import domain.Alert;
import domain.Assessment;
import domain.Car;
import domain.Driver;
import domain.Reclamation;
import domain.Reservation;
import exceptions.RideMustBeLaterThanTodayException;
import exceptions.RideAlreadyExistException;

import javax.jws.WebMethod;
import javax.jws.WebService;
 
/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade  {
	  
	/**
	 * This method returns all the cities where rides depart 
	 * @return collection of cities
	 */
	@WebMethod public List<String> getDepartCities();
	
	/**
	 * This method returns all the arrival destinations, from all rides that depart from a given city  
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	@WebMethod public List<String> getDestinationCities(String from);


	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @param nPlaces available seats
	 * @param driver to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today 
 	 * @throws RideAlreadyExistException if the same ride already exists for the driver
	 */
   @WebMethod
   public Ride createRide(String from, String to, Date date, float price, String driverEmail, Car pCar) throws RideMustBeLaterThanTodayException, RideAlreadyExistException;
	
   
   @WebMethod
   public Car createCar(Driver driver, String matrikula, int nPlazak, String marka);
	
	/**
	 * This method retrieves the rides from two locations on a given date 
	 * 
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride
	 * @param date the date of the ride 
	 * @return collection of rides
	 */
	@WebMethod public List<Ride> getRides(String from, String to, Date date);
	
	/**
	 * This method retrieves from the database the dates a month for which there are events
	 * @param from the origin location of a ride
	 * @param to the destination location of a ride 
	 * @param date of the month for which days with rides want to be retrieved 
	 * @return collection of rides
	 */
	@WebMethod public List<Date> getThisMonthDatesWithRides(String from, String to, Date date);
	
	@WebMethod public Assessment createAssessment(Ride ride, Traveler traveler, int stars, String text);
	
	@WebMethod public boolean confirmReservation(Reservation erreserba);
	
	@WebMethod public boolean acceptReservation(Reservation erreserba);
	
	@WebMethod public boolean rejectReservation(Reservation erreserba);
	
	@WebMethod public boolean createReservation(Traveler traveler, Ride ride, int nPlaces);
	
	@WebMethod public boolean cancelRide(Ride ride);
	
	@WebMethod public boolean updateRide(Ride ride);
	
	@WebMethod public boolean updateReservation(Reservation reservation);
	
	@WebMethod public boolean removeReservation(Reservation reservation);
	
	@WebMethod public boolean updateTraveler(Traveler traveler);
	
	@WebMethod public boolean addMoney(User user,float money);
	
	@WebMethod public boolean withdrawMoney(User user,float money);
	
	@WebMethod public Driver getDriverByName(String driver);
	
	@WebMethod public Ride getRideByParams(Driver driver, int nPlaces, float price, String from, String to, Date date);
	
	@WebMethod public Ride getRideByID(int iD);
	
	@WebMethod public User checkLogin(String user, String password);
	
	@WebMethod public User register(String izena, String email, String pasahitza, String mota);
	
	@WebMethod public List<Reservation> travelersReservations (Traveler traveler);
	
	@WebMethod public List<Ride> ridesByDriver (Driver driver);
	
	@WebMethod public boolean deleteUser (User user);
	
	@WebMethod public List<Reclamation> getReclamation(Traveler trav);

	@WebMethod public boolean reclamation(Traveler traveler, Reservation res, String why);
	
	@WebMethod public List<Reclamation> getReclamationNotProcesed();
	
	@WebMethod public boolean erreklamazioaKudeatu(Reclamation r, int a);
	
	@WebMethod public List<Reservation> getErreserbaBefore(Traveler traveler);
	
	@WebMethod public Reclamation getReclamationByID(int iD);
	
	@WebMethod public Alert createAlert(Traveler u, String nundik, String nora, Date d);
	
	@WebMethod public List<Ride> checkAlerts(Traveler t);

	/**
	 * This method calls the data access to initialize the database with some events and questions.
	 * It is invoked only when the option "initialize" is declared in the tag dataBaseOpenMode of resources/config.xml file
	 */	
	@WebMethod public void initializeBD();

	
}
