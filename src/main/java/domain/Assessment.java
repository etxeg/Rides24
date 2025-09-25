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
public class Assessment implements Serializable {
	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@Id
	private int balorazioZenbakia;
	private Traveler traveler;
	private Driver driver;
	private int stars;
	private String text;
	
	
	public Assessment(Driver driver, Traveler traveler, int stars, String text) {
		super();
		this.driver = driver;
		this.traveler = traveler;
		this.stars = stars;
		this.text = text;
	}


	public int getBalorazioZenbakia() {
		return balorazioZenbakia;
	}


	public void setBalorazioZenbakia(int balorazioZenbakia) {
		this.balorazioZenbakia = balorazioZenbakia;
	}


	public Traveler getTraveler() {
		return traveler;
	}


	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}


	public Driver getDriver() {
		return driver;
	}


	public void setDriver(Driver driver) {
		this.driver = driver;
	}


	public int getStars() {
		return stars;
	}


	public void setStars(int stars) {
		this.stars = stars;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	@Override
	public String toString() {
		return traveler.getName()+": "+this.stars+"/5";
	}
	
	
	
	
}
