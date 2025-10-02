package dataAccess;

import java.io.File;
import java.net.NoRouteToHostException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.objectdb.spi.TrackableUserType;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Admin;
import domain.Alert;
import domain.Assessment;
import domain.Car;
import domain.Driver;
import domain.Movement;
import domain.Reclamation;
import domain.Reservation;
import domain.Ride;
import domain.User;
import domain.Traveler;
import exceptions.RideAlreadyExistException;
import exceptions.RideMustBeLaterThanTodayException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	private EntityManager db;
	private EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess() {
		if (c.isDatabaseInitialized()) {
			String fileName = c.getDbFilename();

			File fileToDelete = new File(fileName);
			if (fileToDelete.delete()) {
				File fileToDeleteTemp = new File(fileName + "$");
				fileToDeleteTemp.delete();

				System.out.println("File deleted");
			} else {
				System.out.println("Operation failed");
			}
		}
		open();
		if (c.isDatabaseInitialized())
			initializeDB();

		System.out.println("DataAccess created => isDatabaseLocal: " + c.isDatabaseLocal() + " isDatabaseInitialized: "
				+ c.isDatabaseInitialized());

		close();

	}

	public DataAccess(EntityManager db) {
		this.db = db;
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();

		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 1;
				year += 1;
			}

			// Create drivers
			Driver driver1 = new Driver("driver1@gmail.com", "Aitor Fernandez", "123");
			Driver driver2 = new Driver("driver2@gmail.com", "Ane Gaztañaga", "123");
			Driver driver3 = new Driver("driver3@gmail.com", "Test Driver", "123");
//			Admin admin1 = new Admin("admin1@gmail.com", "Test Admin", "123");
//			db.persist(admin1);
			// Create travelers
			Traveler traveler1 = new Traveler("traveler1@gmail.com", "Test Traveler", "123");
			traveler1.setMoney(500);
			Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Movement move = new Movement(traveler1.getMovementsCount(), 500, true, date);
			traveler1.addMovement(move);
			db.persist(move);
			Traveler traveler2 = new Traveler("traveler2@gmail.com", "Test Traveler2", "123");
			traveler2.setMoney(500);
			Date date2 = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Movement move2 = new Movement(traveler2.getMovementsCount(), 500, true, date2);
			traveler2.addMovement(move2);
			db.persist(move2);

			Car car1 = new Car("ABC", driver1, 4, "Toyota");
			driver1.addCar(car1);
			Car car2 = new Car("CBA", driver2, 4, "Toyota");
			driver2.addCar(car2);
			Car car3 = new Car("ABA", driver3, 4, "Toyota");
			driver3.addCar(car3);

			// Create rides
			driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 7, car1);
			driver1.addRide("Donostia", "Gazteiz", UtilDate.newDate(year, month, 6), 8, car1);
			driver1.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 4, car1);
			//Ride ride1 = driver1.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 1), 5, car1);
			
			driver1.addRide("Donostia", "Iruña", UtilDate.newDate(year, month, 7), 8, car1);

			driver2.addRide("Donostia", "Bilbo", UtilDate.newDate(year, month, 15), 3, car2);
			driver2.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 25), 5, car2);
			driver2.addRide("Eibar", "Gasteiz", UtilDate.newDate(year, month, 6), 5, car2);

			driver3.addRide("Bilbo", "Donostia", UtilDate.newDate(year, month, 14), 3, car3);

			/*Reservation erreserba = new Reservation(traveler1, ride1, UtilDate.newDate(year, month, 1),5);
			erreserba.baieztatu();
			ride1.addAcceptedReservations(erreserba);
			traveler1.addReservation(erreserba)
			
			db.persist(erreserba);
			db.merge(ride1);*/
			
			db.persist(car1);
			db.persist(car2);
			db.persist(car3);
			db.persist(driver1);
			db.persist(driver2);
			db.persist(driver3);
			db.persist(traveler1);
			db.persist(traveler2);
			

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method returns all the cities where rides depart
	 * 
	 * @return collection of cities
	 */
	public List<String> getDepartCities() {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.from FROM Ride r ORDER BY r.from", String.class);
		List<String> cities = query.getResultList();
		return cities;

	}

	/**
	 * This method returns all the arrival destinations, from all rides that depart
	 * from a given city
	 * 
	 * @param from the depart location of a ride
	 * @return all the arrival destinations
	 */
	public List<String> getArrivalCities(String from) {
		TypedQuery<String> query = db.createQuery("SELECT DISTINCT r.to FROM Ride r WHERE r.from=?1 ORDER BY r.to",
				String.class);
		query.setParameter(1, from);
		List<String> arrivingCities = query.getResultList();
		return arrivingCities;

	}

	/**
	 * This method creates a ride for a driver
	 * 
	 * @param from        the origin location of a ride
	 * @param to          the destination location of a ride
	 * @param date        the date of the ride
	 * @param nPlaces     available seats
	 * @param driverEmail to which ride is added
	 * 
	 * @return the created ride, or null, or an exception
	 * @throws RideMustBeLaterThanTodayException if the ride date is before today
	 * @throws RideAlreadyExistException         if the same ride already exists for
	 *                                           the driver
	 */
	public Ride createRide(String from, String to, Date date, float price, String driverEmail, Car pCar)
			throws RideAlreadyExistException, RideMustBeLaterThanTodayException {
		System.out.println(">> DataAccess: createRide=> from= " + from + " to= " + to + " driver=" + driverEmail
				+ " date " + date);
		try {
			if (new Date().compareTo(date) > 0) {
				throw new RideMustBeLaterThanTodayException(
						ResourceBundle.getBundle("Etiquetas").getString("CreateRideGUI.ErrorRideMustBeLaterThanToday"));
			}
			db.getTransaction().begin();

			Driver driver = db.find(Driver.class, driverEmail);
			if (driver.doesRideExists(from, to, date)) {
				db.getTransaction().commit();
				throw new RideAlreadyExistException(
						ResourceBundle.getBundle("Etiquetas").getString("DataAccess.RideAlreadyExist"));
			}
			Ride ride = driver.addRide(from, to, date, price, pCar);
			// next instruction can be obviated
			db.persist(driver);
			db.getTransaction().commit();

			return ride;
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			db.getTransaction().commit();
			return null;
		}

	}
	
	public Alert createAlert(Traveler u, String nundik, String nora, Date data) {
		 try {
				db.getTransaction().begin();
				Alert actualAlert = u.addAlert(nundik, nora, data);
				// db.persist(actualAlert);
				db.merge(u);
				db.getTransaction().commit();
				return actualAlert;	
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Error");
				e.printStackTrace();
				db.getTransaction().commit();
				return null;
			}
	}

	public Assessment createAssessment(Ride ride, Traveler traveler, int stars, String text) {
		try {
			db.getTransaction().begin();

			Assessment actualAssessment = ride.addAssessment(traveler, stars, text);
			db.persist(actualAssessment);
			db.merge(ride.getDriver());
			db.getTransaction().commit();
			return actualAssessment;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
			e.printStackTrace();
			db.getTransaction().commit();
			return null;
		}
	}

	public Car createCar(Driver driver, String matrikula, int nPlazak, String marka) {
		try {
			db.getTransaction().begin();
			TypedQuery<Car> query = db.createQuery("SELECT r FROM Car r WHERE r.matrikula=?1", Car.class);
			query.setParameter(1, matrikula);

			List<Car> driverList = query.getResultList();
			if (driverList.isEmpty()) {
				Car actualCar = new Car(matrikula, driver, nPlazak, marka);
				driver.addCar(actualCar);
				db.persist(actualCar);
				db.merge(driver);
				db.getTransaction().commit();
				return actualCar;
			} else { // Kotxea dagoeneko existitzen da
				db.getTransaction().commit();
				return null;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Error");
			e.printStackTrace();
			db.getTransaction().commit();
			return null;
		}

	}

	/**
	 * This method retrieves the rides from two locations on a given date
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date the date of the ride
	 * @return collection of rides
	 */
	public List<Ride> getRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getRides=> from= " + from + " to= " + to + " date " + date);

		List<Ride> res = new ArrayList<>();
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",
				Ride.class);
		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, date);
		List<Ride> rides = query.getResultList();
		for (Ride ride : rides) {
			if (ride.isCancelled() == false) {
				res.add(ride);
			}
		}
		return res;
	}

	public boolean confirmReservation(Reservation erreserba) {
		try {
			db.getTransaction().begin();
			erreserba.baieztatu();

			// Dirua gehitu
			float money = erreserba.getPrezioa();
			erreserba.getRide().getDriver().addMoney(money);

			Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Movement move = new Movement(erreserba.getTraveler().getMovementsCount(), money, true, date);
			erreserba.getTraveler().addMovement(move);

			db.merge(erreserba.getRide().getDriver());
			db.merge(erreserba);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean acceptReservation(Reservation erreserba) {
		try {
			db.getTransaction().begin();
			erreserba.onartu();
			erreserba.getRide().removePendingReservation(erreserba);
			erreserba.getRide().addAcceptedReservations(erreserba);
			db.merge(erreserba);
			db.merge(erreserba.getRide());
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean rejectReservation(Reservation erreserba) {
		try {
			db.getTransaction().begin();
			erreserba.getRide().removePendingReservation(erreserba);
			erreserba.getTraveler().removeReservation(erreserba);
			erreserba.getRide().addNChairs(erreserba.getnSeats());

			float addMoney = erreserba.getTraveler().getMoney() + erreserba.getPrezioa();
			erreserba.getTraveler().setMoney(addMoney);
			Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Movement move = new Movement(erreserba.getTraveler().getMovementsCount(), addMoney, true, date);
			erreserba.getTraveler().addMovement(move);

			db.merge(erreserba.getTraveler());
			db.merge(erreserba.getRide());
			// Erreserba datu basetik ezabatu
			erreserba = db.merge(erreserba);
			db.remove(erreserba);

			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createReservation(Traveler traveler, Ride ride, int nPlaces) {
		try {
			db.getTransaction().begin();
			Calendar today = Calendar.getInstance();
			int month = today.get(Calendar.MONTH);
			int year = today.get(Calendar.YEAR);
			int day = today.get(Calendar.DAY_OF_MONTH);
			Reservation erreserbaClass = new Reservation(traveler, ride, UtilDate.newDate(year, month, day),
					ride.getPrice() * nPlaces);
			if (erreserbaClass != null) {

				float prezioa = nPlaces * ride.getPrice();
				float travelerMoney = traveler.getMoney();
				if (travelerMoney >= prezioa) {
					ride.addPendingReservation(erreserbaClass);
					traveler.addReservation(erreserbaClass);
					traveler.setMoney(travelerMoney - prezioa);
					Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
					Movement move = new Movement(traveler.getMovementsCount(), prezioa, false, date);
					traveler.addMovement(move);
					erreserbaClass.setnSeats(nPlaces);
					ride.deleteNChairs(nPlaces);

					db.persist(erreserbaClass);
					db.merge(ride);
					db.merge(traveler);
					System.out.println("Erreserba zenbakia: " + erreserbaClass.getErreserbaZenbakia());
				} else {
					System.out.println("Dirua falta da");
				}
			}
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean cancelRide(Ride ride) {
		try {
			db.getTransaction().begin();
			ride.cancelRide();
			for (Reservation reservation : ride.getAcceptedReservations()) { // Dirua bueltatu
				float money = reservation.getTraveler().getMoney() + reservation.getPrezioa();
				reservation.getTraveler().setMoney(money);
				Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Movement move = new Movement(reservation.getTraveler().getMovementsCount(), reservation.getPrezioa(),
						true, date);
				reservation.getTraveler().addMovement(move);

				reservation.cancel();

				db.merge(reservation);
				db.merge(reservation.getTraveler());
			}

			Driver driver = ride.getDriver();
			driver.cancelRide(ride);

			db.merge(ride);
			db.merge(driver);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean cancelRides(Ride ride) {
		try {
			ride.cancelRide();
			for (Reservation reservation : ride.getAcceptedReservations()) { // Dirua bueltatu
				float money = reservation.getTraveler().getMoney() + reservation.getPrezioa();
				reservation.getTraveler().setMoney(money);
				Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Movement move = new Movement(reservation.getTraveler().getMovementsCount(), reservation.getPrezioa(),
						true, date);
				reservation.getTraveler().addMovement(move);

				reservation.cancel();

				db.merge(reservation);
				db.merge(reservation.getTraveler());
			}

			Driver driver = ride.getDriver();
			driver.cancelRide(ride);

			db.merge(ride);
			db.merge(driver);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateRide(Ride ride) {
		try {
			db.getTransaction().begin();
			db.merge(ride);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateReservation(Reservation reservation) {
		try {
			db.getTransaction().begin();
			db.merge(reservation);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean removeReservation(Reservation reservation) {
		try {
			db.getTransaction().begin();
			TypedQuery<Driver> query = db.createQuery("DELETE r FROM Reservation r WHERE r.erreserbaZenbakia=?1",
					Driver.class);
			query.setParameter(1, reservation.getErreserbaZenbakia());
			// db.remove(reservation);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateTraveler(Traveler traveler) {
		try {
			db.getTransaction().begin();
			db.merge(traveler);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean addMoney(User user, float money) {
		try {
			db.getTransaction().begin();
			user.addMoney(money);
			Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Movement move = new Movement(user.getMovementsCount(), money, true, date);
			user.addMovement(move);

			db.persist(move);
			db.merge(user);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean withdrawMoney(User user, float money) {
		try {
			db.getTransaction().begin();
			if (user.getMoney() >= money) {
				user.withdrawMoney(money);
				Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
				Movement move = new Movement(user.getMovementsCount(), money, false, date);
				user.addMovement(move);
				db.persist(move);
				db.merge(user);
				db.getTransaction().commit();
				return true;
			} else {
				db.getTransaction().commit();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public Driver getDriverByName(String driver) {
		TypedQuery<Driver> query = db.createQuery("SELECT r FROM User r WHERE r.name=?1", Driver.class);
		query.setParameter(1, driver);

		List<Driver> driverList = query.getResultList();
		if (!driverList.isEmpty()) {
			Driver actualDriver = driverList.get(0);
			return actualDriver;

		} else {
			return null;
		}

	}

	public Ride getRideByParams(Driver driver, int nPlaces, float price, String from, String to, Date date) {
		TypedQuery<Ride> query = db
				.createQuery("SELECT r FROM Ride r WHERE r.driver=?1 AND r.nPlaces=?2 AND r.price=?3", Ride.class);
		query.setParameter(1, driver);
		query.setParameter(2, nPlaces);
		query.setParameter(3, price);

		List<Ride> rideList = query.getResultList();
		if (!rideList.isEmpty()) {
			Ride actualRide = rideList.get(0);
			return actualRide;

		} else {
			return null;
		}

	}

	public Ride getRideByID(int iD) {
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.driver=?1", Ride.class);
		query.setParameter(1, iD);

		List<Ride> rideList = query.getResultList();
		if (!rideList.isEmpty()) {
			Ride actualRide = rideList.get(0);
			return actualRide;

		} else {
			return null;
		}

	}

	public User checkLogin(String name, String password) {
		TypedQuery<User> query = db.createQuery("SELECT r FROM User r WHERE r.name=?1 AND r.password=?2", User.class);
		query.setParameter(1, name);
		query.setParameter(2, password);

		List<User> userList = query.getResultList();
		if (!userList.isEmpty()) {
			User actualUser = userList.get(0);
			return actualUser;

		} else {
			return null;
		}

	}
	
	public Reclamation getReclamationByID(int iD) {
		TypedQuery<Reclamation> query = db.createQuery("SELECT r FROM Reclamation r WHERE r.erreklamazioZenbakia=?1",Reclamation.class);   
		query.setParameter(1, iD);
		
		List<Reclamation> rideList = query.getResultList();
		if (!rideList.isEmpty()) {
			Reclamation actualRide = rideList.get(0);
		 	return actualRide;
			
		} else {
			return null;
		}
		
	}
	
	public boolean deleteUser(User user) {
		try {
			db.getTransaction().begin();
			
			if (user instanceof Traveler) {
				Traveler t = (Traveler) user;
				for (Reservation reservation:t.getReservations()) {
					reservation.cancelReservation();
				}
			} else if (user instanceof Driver) {
				Driver d = (Driver) user;
				List<Ride> ridesCopy = new ArrayList<>(d.getRides());
				for (Ride ride:ridesCopy) {
					cancelRides(ride);
				}
			} else if (user instanceof Admin) {
				System.out.println("Ezin da administratzaile bat ezabatu.");
				db.getTransaction().commit();
				return true;
			} else {
				db.getTransaction().commit();
				return false;
			}
			User managedUser = db.find(User.class, user);
			db.remove(managedUser);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			db.getTransaction().rollback();
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean reclamation(Traveler traveler, Reservation res, String why) {
		try {
			db.getTransaction().begin();
			//Ride r = getRideByID(zenb);
			//if (r==null) {
			//	return false;
			//}
			System.out.println("Iritsi da");
			//Driver driver =r.getDriver();
			Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			String state="prozesatu-gabea";
			Reclamation reclam = new Reclamation(traveler, res, date, state, why);
			traveler.addReclamation(reclam);
			//driver.addReclamation(reclam);
			db.persist(reclam);
			db.merge(traveler);
			db.getTransaction().commit();
			return true;
		}catch(Exception e) {
			System.out.println(e.getCause());
			System.out.println(e.getMessage());
			db.getTransaction().commit();
			return false;
		}
		
	}
	
	public List<Reservation> getErreserbaBefore(Traveler traveler){
		db.getTransaction().begin();
		Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
		TypedQuery<Reservation> query = db.createQuery("SELECT DISTINCT r FROM Reservation r WHERE r.date<?1 AND r.traveler=?2",Reservation.class);
		query.setParameter(1, date);
		query.setParameter(2, traveler);		
		List<Reservation> reservationList = query.getResultList();
		return reservationList;
		
	}
	
	public List<Reclamation> getReclamationNotProcesed(){
		db.getTransaction().begin();
		TypedQuery<Reclamation> query = db.createQuery("SELECT DISTINCT r FROM Reclamation r WHERE r.state=?1",Reclamation.class);
		query.setParameter(1, "prozesatu-gabea");
		List<Reclamation> reclamList = query.getResultList();
		return reclamList;
		
	}
	
	public List<Reclamation> getReclamation(Traveler traveler){
		db.getTransaction().begin();
		TypedQuery<Reclamation> query = db.createQuery("SELECT r FROM Reclamation r WHERE r.traveler=?1",Reclamation.class);
		query.setParameter(1, traveler);
		List<Reclamation> reclamList = query.getResultList();
		return reclamList;
	}
	
	public boolean erreklamazioaKudeatu(Reclamation r, int a) {
		db.getTransaction().begin();
		System.out.println("Iritsi da");
		if (a==1) {
			r.onartu();
			Reservation res =r.getReservation();
			Ride ri = res.getRide();
			Float pri = ri.getPrice();
			Traveler tr = r.getTraveler();
			tr.addMoney(pri);
			Date date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
			Movement move = new Movement(tr.getMovementsCount(), pri, true, date);
			tr.addMovement(move);
			db.merge(tr);
			db.merge(r);
			db.getTransaction().commit();
			return true;
			
		}else {
			r.baztertu();
			db.merge(r);
			db.getTransaction().commit();
			return true;
			
		}
	}

	public User register(String name, String email, String password, String mota) {
		try {
			db.getTransaction().begin();
			TypedQuery<User> query = db.createQuery("SELECT r FROM User r WHERE r.name=?1 OR r.email=?2",User.class);
			query.setParameter(1, name);
			query.setParameter(2, email);
			List<User> userList = query.getResultList();
			
			if (userList.isEmpty()) {
				if (mota==ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Driver")) {
					Driver dri = new Driver(email, name, password);
					db.persist(dri);
					db.getTransaction().commit();
					return(dri);
					
				}else if (mota==ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Traveler")) {
					Traveler tra = new Traveler(email, name, password);
					db.persist(tra);
					db.getTransaction().commit();
					return(tra);
				} else if(mota==ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.Admin")){
					Admin adm = new Admin(email, name, password);
					db.persist(adm);
					db.getTransaction().commit();
					return(adm);
				}else {
					return(null);
				}
				
				
			} else {
				db.getTransaction().commit();
				return(null);
			}
			
		}catch(Exception e) {
			return(null);
		}
		
		
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param from the origin location of a ride
	 * @param to   the destination location of a ride
	 * @param date of the month for which days with rides want to be retrieved
	 * @return collection of rides
	 */
	public List<Date> getThisMonthDatesWithRides(String from, String to, Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		List<Date> res = new ArrayList<>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT r.date FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date BETWEEN ?3 and ?4",
				Date.class);

		query.setParameter(1, from);
		query.setParameter(2, to);
		query.setParameter(3, firstDayMonthDate);
		query.setParameter(4, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			res.add(d);
		}
		return res;
	}

	public void open() {

		String fileName = c.getDbFilename();
		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);
			db = emf.createEntityManager();
		}
		System.out.println("DataAccess opened => isDatabaseLocal: " + c.isDatabaseLocal());

	}

	public void close() {
		db.close();
		System.out.println("DataAcess closed");
	}

	public List<Reservation> getAllReservationsByTraveler(Traveler traveler) {
		TypedQuery<Reservation> query = db.createQuery("SELECT re FROM Reservation re WHERE re.traveler=?1",
				Reservation.class);
		query.setParameter(1, traveler);
		List<Reservation> reservations = query.getResultList();
		return reservations;
	}
	
	public List<Ride> checkAlerts(Traveler traveler) {
		db.getTransaction().begin();
		List<Ride> rideResult = new ArrayList<Ride>();
		List<Alert> removeAlerts = new ArrayList<Alert>();
		for (Alert actualAlert : traveler.getAlerts()) {
			TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.from=?1 AND r.to=?2 AND r.date=?3",
					Ride.class);
			query.setParameter(1, actualAlert.getNundik());
			query.setParameter(2, actualAlert.getNora());
			query.setParameter(3, actualAlert.getData());		
			List<Ride> rideList = query.getResultList();
			if (rideList.isEmpty() == false) {
				rideResult.addAll(rideList);
				removeAlerts.add(actualAlert);
			}
		}
		traveler.removeAlertList(removeAlerts);
		for (Alert alert:removeAlerts) {
			Alert managed = db.merge(alert);
		    db.remove(managed);
		}
		db.merge(traveler);
		db.getTransaction().commit();
		return rideResult;
	}

	public List<Ride> getAllRidesByDriver(Driver driver) {
		List<Ride> res = new ArrayList<>();
		TypedQuery<Ride> query = db.createQuery("SELECT r FROM Ride r WHERE r.driver=?1", Ride.class);
		query.setParameter(1, driver);
		List<Ride> rides = query.getResultList();
		for (Ride ride : rides) {
			if (ride.isCancelled() == false) {
				res.add(ride);
			}
		}
		return res;

	}
}
