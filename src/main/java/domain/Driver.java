package domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Driver extends User implements Serializable  {
	
	/**
	 * 
	 */
	@XmlIDREF
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Ride> rides=new Vector<Ride>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	private List<Car> cars=new Vector<Car>();
	
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private List<Assessment> assessments=new Vector<Assessment>();

	public Driver() {
		super();
	}

	public Driver(String email, String name,String password) {
		super(email,name,password);
	}
	
	
	/**
	 * This method creates a bet with a question, minimum bet ammount and percentual profit
	 * 
	 * @param question to be added to the event
	 * @param betMinimum of that question
	 * @return Bet
	 */
	public Ride addRide(String from, String to, Date date, float price, Car pCar)  {
        Ride ride=new Ride(from,to,date,price, this,pCar);
        rides.add(ride);
        return ride;
	}

	public void addCar(Car pCar)  {
        cars.add(pCar);
	}
	
	/**
	 * This method checks if the ride already exists for that driver
	 * 
	 * @param from the origin location 
	 * @param to the destination location 
	 * @param date the date of the ride 
	 * @return true if the ride exists and false in other case
	 */
	public boolean doesRideExists(String from, String to, Date date)  {	
		for (Ride r:rides)
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			 return true;
		
		return false;
	}

	public Ride removeRide(String from, String to, Date date) {
		boolean found=false;
		int index=0;
		Ride r=null;
		while (!found && index<=rides.size()) {
			r=rides.get(++index);
			if ( (java.util.Objects.equals(r.getFrom(),from)) && (java.util.Objects.equals(r.getTo(),to)) && (java.util.Objects.equals(r.getDate(),date)) )
			found=true;
		}
			
		if (found) {
			rides.remove(index);
			return r;
		} else return null;
	}
	
	public void printCarList() {
		for (Car actualCar: this.cars) {
			System.out.println(actualCar);
		}
	}
	
	public List<Ride> getRides() {
		return rides;
	}

	public void setRides(List<Ride> rides) {
		this.rides = rides;
	}

	public List<Car> getCars() {
		return cars;
	}
	
	public void cancelRide(Ride ride) {
		this.rides.remove(ride);
	}
	
	public Assessment addAssessment(Traveler traveler, int stars, String text)  {
        Assessment assessment=new Assessment(this, traveler, stars, text);
        assessments.add(assessment);
        return assessment;
	}

	public List<Assessment> getAssessments() {
		return assessments;
	}
}
