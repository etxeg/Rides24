package domain;

import java.util.Calendar;
import java.util.LinkedList;

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

import configuration.UtilDate;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Traveler extends User{
	private static final long serialVersionUID = 1L;
	@XmlIDREF
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Reservation> reservations = new Vector<Reservation>();
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Reclamation> reclamations = new Vector<Reclamation>();
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Alert> alerts=new Vector<Alert>();
	public Traveler() {
		super();
	}

	public Traveler(String email, String name,String password) {
		super(email,name,password);
	}
	
	
	public void addReservation(Reservation pReservation) {
		this.reservations.add(pReservation);
	}
	
	public void addReclamation(Reclamation reclam) {
		this.reclamations.add(reclam);
	}
	
	public void removeReservation(Reservation pReservation) {
		this.reservations.remove(pReservation);
	}
	
	public boolean containsReservation(Ride pRide) {
		boolean erantzuna = false;
		for (Reservation reservationi : this.reservations) {
			if (pRide.getRideNumber() == reservationi.getRide().getRideNumber()) {
				erantzuna = true;
			}
		}
		return erantzuna;
	}
	
	public void printReservations() {
		for (Reservation reservation:this.reservations) {
			System.out.println("Erreserba zenbakia: "+reservation.getErreserbaZenbakia()+"; Onartua: "+reservation.isOnartua());
		}
	}

	public List<Reservation> getReservations() {
		return reservations;
	}

	public void setReservations(LinkedList<Reservation> reservations) {
		this.reservations = reservations;
	}
	
	
	public List<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
	
	public void printAlerts() {
		for (Alert actualAlert: this.alerts) {
			System.out.println(actualAlert);
		}
	}
	
	public List<Reclamation> getReclamations() {
		return reclamations;
	}

	public Alert addAlert(String nundik, String nora, Date data) {
		Alert alert=new Alert(this, nundik, nora, data);
	    alerts.add(alert);
	    return alert;
	}
	
	public void removeAlertList(List<Alert> removeAlerts) {
		for (Alert alert:removeAlerts) {
			alerts.remove(alert);
		}		
	}	
	
}
