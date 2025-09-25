package domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
public class Reclamation {
	@GeneratedValue
	private int erreklamazioZenbakia;
	private Traveler traveler;
	private Reservation ride;
	private Date date;
	private String state;
	private String why;
	
	
	public Reclamation(Traveler traveler, Reservation ride, Date date, String state, String why) {
		super();
		this.traveler = traveler;
		this.ride = ride;
		this.date = date;
		this.state = state;
		this.why = why;
	}
	public int getErreklamazioZenbakia() {
		return erreklamazioZenbakia;
	}
	public void setErreklamazioZenbakia(int erreklamazioZenbakia) {
		this.erreklamazioZenbakia = erreklamazioZenbakia;
	}
	public Traveler getTraveler() {
		return traveler;
	}
	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}
	public Reservation getReservation() {
		return ride;
	}
	public void setReservation(Reservation ride) {
		this.ride = ride;
	}
	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void onartu() {
		this.state = "Onartua";
	}
	
	public void baztertu() {
		this.state = "Baztertua";
	}
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getWhy() {
		return why;
	}
	public void setWhy(String why) {
		this.why = why;
	}
	
	@Override
	public String toString() {
		return traveler.getName()+ " -> " + ride;
	}

}