package businessLogic;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import dataAccess.DataAccess;
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

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");

		dbManager = new DataAccess();

		// dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		dbManager = da;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<String> getDepartCities() {
		dbManager.open();

		List<String> departLocations = dbManager.getDepartCities();

		dbManager.close();

		return departLocations;

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<String> getDestinationCities(String from) {
		dbManager.open();

		List<String> targetCities = dbManager.getArrivalCities(from);

		dbManager.close();

		return targetCities;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public Ride createRide(String from, String to, Date date, float price, String driverEmail, Car pCar)
			throws RideMustBeLaterThanTodayException, RideAlreadyExistException {

		dbManager.open();
		String rideInfo = from+"/"+to+"/"+driverEmail;
		Ride ride = dbManager.createRide(rideInfo, date, price, pCar);
		dbManager.close();
		return ride;
	};

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Ride> getRides(String from, String to, Date date) {
		dbManager.open();
		List<Ride> rides = dbManager.getRides(from, to, date);
		dbManager.close();
		return rides;
	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		dbManager.open();
		List<Date> dates = dbManager.getThisMonthDatesWithRides(from, to, date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess();

		dB4oManager.close();

	}

	/**
	 * {@inheritDoc}
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open();
		dbManager.initializeDB();
		dbManager.close();
	}

	@Override
	public Assessment createAssessment(Ride ride, Traveler traveler, int stars, String text) {
		dbManager.open();
		Assessment assessment = dbManager.createAssessment(ride, traveler, stars, text);
		dbManager.close();
		return assessment;
	}

	@Override
	public Car createCar(Driver driver, String matrikula, int nPlazak, String marka) {
		dbManager.open();
		Car car = dbManager.createCar(driver, matrikula, nPlazak, marka);
		dbManager.close();
		return car;
	};

	@Override
	public boolean confirmReservation(Reservation erreserba) {
		dbManager.open();
		boolean erantzuna = dbManager.confirmReservation(erreserba);
		dbManager.close();
		return erantzuna;
	}

	@Override
	public boolean acceptReservation(Reservation erreserba) {
		dbManager.open();
		boolean erantzuna = dbManager.acceptReservation(erreserba);
		dbManager.close();
		return erantzuna;
	}

	@Override
	public boolean rejectReservation(Reservation erreserba) {
		dbManager.open();
		boolean erantzuna = dbManager.rejectReservation(erreserba);
		dbManager.close();
		return erantzuna;
	}

	@Override
	public boolean createReservation(Traveler traveler, Ride ride, int nPlaces) {
		dbManager.open();
		boolean erantzuna = dbManager.createReservation(traveler, ride, nPlaces);
		dbManager.close();
		return erantzuna;
	}

	@Override
	public boolean updateRide(Ride ride) {
		dbManager.open();
		boolean erantzuna = dbManager.updateRide(ride);
		dbManager.close();
		return erantzuna;
	}

	public boolean cancelRide(Ride ride) {
		dbManager.open();
		boolean erantzuna = dbManager.cancelRide(ride);
		dbManager.close();
		return erantzuna;
	}

	public boolean updateReservation(Reservation reservation) {
		dbManager.open();
		boolean erantzuna = dbManager.updateReservation(reservation);
		dbManager.close();
		return erantzuna;
	}

	public boolean removeReservation(Reservation reservation) {
		dbManager.open();
		boolean erantzuna = dbManager.removeReservation(reservation);
		dbManager.close();
		return erantzuna;
	}

	public boolean updateTraveler(Traveler traveler) {
		dbManager.open();
		boolean erantzuna = dbManager.updateTraveler(traveler);
		dbManager.close();
		return erantzuna;
	}

	public boolean addMoney(User user, float money) {
		dbManager.open();
		boolean erantzuna = dbManager.addMoney(user, money);
		dbManager.close();
		return erantzuna;
	}

	public boolean withdrawMoney(User user, float money) {
		dbManager.open();
		boolean erantzuna = dbManager.withdrawMoney(user, money);
		dbManager.close();
		return erantzuna;
	}

	public Driver getDriverByName(String driver) {
		dbManager.open();
		Driver erantzuna = dbManager.getDriverByName(driver);
		dbManager.close();
		return erantzuna;
	}

	public Ride getRideByParams(Driver driver, int nPlaces, float price, String from, String to, Date date) {
		dbManager.open();
		Ride erantzuna = dbManager.getRideByParams(driver, nPlaces, price, from, to, date);
		dbManager.close();
		return erantzuna;
	}

	public Ride getRideByID(int iD) {
		dbManager.open();
		Ride erantzuna = dbManager.getRideByID(iD);
		dbManager.close();
		return erantzuna;
	}

	public User checkLogin(String user, String password) {
		dbManager.open();
		User erantzuna = dbManager.checkLogin(user, password);
		dbManager.close();
		return erantzuna;
	}

	public User register(String izena, String email, String pasahitza, String mota) {
		dbManager.open();
		User erantzuna = dbManager.register(izena, email, pasahitza, mota);
		dbManager.close();
		return erantzuna;
	}
	
	@Override
    public Alert createAlert(Traveler u, String nundik, String nora, Date data ) {
    	dbManager.open();
 		Alert alert=dbManager.createAlert(u, nundik, nora, data);	
 		dbManager.close();
 		return alert;
    }

	@WebMethod
	public List<Reservation> travelersReservations(Traveler traveler) {
		dbManager.open();
		List<Reservation> allReservations = dbManager.getAllReservationsByTraveler(traveler);
		dbManager.close();
		return allReservations;
	}

	@WebMethod
	public List<Ride> ridesByDriver(Driver driver) {
		dbManager.open();
		List<Ride> allRidesDriver = dbManager.getAllRidesByDriver(driver);
		dbManager.close();
		return allRidesDriver;
	}

	public boolean deleteUser(User user) {
		dbManager.open();
		boolean result = dbManager.deleteUser(user);
		dbManager.close();
		return result;
	}
	
	public boolean reclamation(Traveler traveler, Reservation res, String why) {
		dbManager.open();
		boolean erantzuna = dbManager.reclamation(traveler, res, why);
		dbManager.close();
		return erantzuna;
	}
	
	public Reclamation getReclamationByID(int iD) {
		dbManager.open();
		Reclamation erantzuna = dbManager.getReclamationByID(iD);
		dbManager.close();
		return erantzuna;
	}
	
	public List<Reclamation> getReclamationNotProcesed(){
		dbManager.open();
		List<Reclamation> erantzuna = dbManager.getReclamationNotProcesed();
		dbManager.close();
		return erantzuna;
	}
	
	public boolean erreklamazioaKudeatu(Reclamation r, int a) {
		dbManager.open();
		boolean erantzuna = dbManager.erreklamazioaKudeatu(r, a);
		dbManager.close();
		return erantzuna;
	}
	
	public List<Ride> checkAlerts(Traveler t) {
		dbManager.open();
		List<Ride> erantzuna = dbManager.checkAlerts(t);
		dbManager.close();
		return erantzuna;
	}
	
	public List<Reservation> getErreserbaBefore(Traveler traveler){
		dbManager.open();
		List<Reservation> erantzuna = dbManager.getErreserbaBefore(traveler);
		dbManager.close();
		return erantzuna;
	}
	
	public List<Reclamation> getReclamation(Traveler trav){
		dbManager.open();
		List<Reclamation> erantzuna = dbManager.getReclamation(trav);
		dbManager.close();
		return erantzuna;
	}
}
