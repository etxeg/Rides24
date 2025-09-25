package domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Reservation implements Serializable {
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@Id
	private int erreserbaZenbakia;
	private Traveler traveler;
	private Ride ride;
	private Date date;
	private float prezioa;
	private String state;
	private int nSeats;
	
	
	public Reservation(Traveler pTraveler, Ride pRide, Date pData, float pPrezioa) {
		super();
		this.traveler = pTraveler;
		this.ride = pRide;
		this.date = pData;
		this.prezioa = pPrezioa;
		this.state = "Ez-onartua";
		this.nSeats = 0;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public Ride getRide() {
		return ride;
	}

	public void setRide(Ride ride) {
		this.ride = ride;
	}

	public int getErreserbaZenbakia() {
		return erreserbaZenbakia;
	}

	public void setErreserbaZenbakia(int erreserbaZenbakia) {
		this.erreserbaZenbakia = erreserbaZenbakia;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public float getPrezioa() {
		return prezioa;
	}

	public void setPrezioa(float prezioa) {
		this.prezioa = prezioa;
	}

	public boolean isOnartua() {
		if (state.equals("Onartua")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isBaieztatua() {
		if (state.equals("Baieztatua")) {
			return true;
		} else {
			return false;
		}
	}

	public String getState() {
		return this.state;
	}
	
	public void onartu() {
		this.state = "Onartua";
	}
	
	public void baieztatu() {
		this.state = "Baieztatua";
	}
	
	public void cancel() {
		this.state = "Kantzelatua";
	}
	
	public void cancelReservation() {
		this.traveler = null;
		this.ride.removeAcceptedReservations(this);
		this.ride=null;
	}

	public int getnSeats() {
		return nSeats;
	}

	public void setnSeats(int nSeats) {
		this.nSeats = nSeats;
	}
	
	public String toString(){
		return this.ride.toString();
	}
	
}
