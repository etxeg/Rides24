package domain;

import java.io.*;
import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Ride implements Serializable {
	@XmlID
	@Id 
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	private Integer rideNumber;
	private String from;
	private String to;
	private int nPlaces;
	private Date date;
	private float price;
	private Car car;
	private boolean cancelled;
	
	private Driver driver;  
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	private List<Reservation> acceptedReservations = new Vector<Reservation>();
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	private List<Reservation> pendingReservations = new Vector<Reservation>();
	
	public Ride(){
		super();
	}
	
	public Ride(Integer pRideNumber, String pFrom, String pTo, Date pDate, float pPrice, Driver pDriver, Car pCar) {
		super();
		this.rideNumber = pRideNumber;
		this.from = pFrom;
		this.to = pTo;
		this.nPlaces = pCar.getPlazak();
		this.date=pDate;
		this.price=pPrice;
		this.driver = pDriver;
		this.car = pCar;
		this.cancelled = false;
	}

	

	public Ride(String pFrom, String pTo,  Date pDate, float pPrice, Driver pDriver, Car pCar) {
		super();
		this.from = pFrom;
		this.to = pTo;
		this.nPlaces = pCar.getPlazak();
		this.date=pDate;
		this.price=pPrice;
		this.driver = pDriver;
		this.car = pCar;
		this.cancelled = false;
	}
	
	public void addPendingReservation(Reservation pReservation) {
		System.out.println("Ride erreserba: " + pReservation.getErreserbaZenbakia());
		this.pendingReservations.add(pReservation);
	}
	
	public void removePendingReservation(Reservation pReservation) {
		this.pendingReservations.remove(pReservation);
	}
	
	public void removeAcceptedReservations(Reservation pReservation) {
		this.acceptedReservations.remove(pReservation);
		this.addNChairs(1);
	}
	
	public void addAcceptedReservations(Reservation pReservation) {
		this.acceptedReservations.add(pReservation);
	}
	
	public void printPendingReservations() {
		for (Reservation reservation:this.pendingReservations) {
			System.out.println(reservation.getErreserbaZenbakia());
		}
	}
	
	public void deleteNChairs(int n) {
		this.nPlaces = this.nPlaces-n;
	}
	public void addNChairs(int n) {
		this.nPlaces = this.nPlaces+n;
	}
	/**
	 * Get the  number of the ride
	 * 
	 * @return the ride number
	 */
	public Integer getRideNumber() {
		return rideNumber;
	}

	
	/**
	 * Set the ride number to a ride
	 * 
	 * @param ride Number to be set	 */
	
	public void setRideNumber(Integer rideNumber) {
		this.rideNumber = rideNumber;
	}


	/**
	 * Get the origin  of the ride
	 * 
	 * @return the origin location
	 */

	public String getFrom() {
		return from;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param origin to be set
	 */	
	
	public void setFrom(String origin) {
		this.from = origin;
	}

	/**
	 * Get the destination  of the ride
	 * 
	 * @return the destination location
	 */

	public String getTo() {
		return to;
	}


	/**
	 * Set the origin of the ride
	 * 
	 * @param destination to be set
	 */	
	public void setTo(String destination) {
		this.to = destination;
	}
	
	/**
	 * Get the date  of the ride
	 * 
	 * @return the ride date 
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set the date of the ride
	 * 
	 * @param date to be set
	 */	
	public void setDate(Date date) {
		this.date = date;
	}

	
	public int getnPlaces() {
		return nPlaces;
	}

	/**
	 * Set the free places of the ride
	 * 
	 * @param  nPlaces places to be set
	 */

	public void setBetMinimum(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	/**
	 * Get the driver associated to the ride
	 * 
	 * @return the associated driver
	 */
	public Driver getDriver() {
		return driver;
	}

	/**
	 * Set the driver associated to the ride
	 * 
	 * @param driver to associate to the ride
	 */
	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<Reservation> getAcceptedReservations() {
		return acceptedReservations;
	}

	public void setAcceptedReservations(LinkedList<Reservation> acceptedReservations) {
		this.acceptedReservations = acceptedReservations;
	}

	public List<Reservation> getPendingReservations() {
		return pendingReservations;
	}

	public void setPendingReservations(LinkedList<Reservation> pendingReservations) {
		this.pendingReservations = pendingReservations;
	}

	public void setnPlaces(int nPlaces) {
		this.nPlaces = nPlaces;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}
	
	public void cancelRide() {
		this.cancelled = true;
	}
	
	public boolean isCancelled() {
		return cancelled;
	}
	
	public Assessment addAssessment(Traveler traveler, int stars, String text)  {
        Assessment assessment = driver.addAssessment(traveler, stars, text);
        return assessment;
	}
	
	@Override
	public String toString(){ 
		int year  = date.getYear()  + 1900;
	    int month = date.getMonth() + 1;
	    int day   = date.getDate(); 
	    return from + " - " + to + ": " + year + "/" + month + "/" + day;
	}



	
}
