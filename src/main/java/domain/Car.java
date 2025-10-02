package domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Car  {
	
	//Prueba automatizatzeko SonarCloud
	/**
	 * 
	 */
	@XmlIDREF
	@Id
	private String matrikula;
	private Driver driver;
	private int plazak;
	private String marka;

	public Car(String pMatrikula, Driver pDriver, int pPlazak, String pMarka) {
		this.matrikula = pMatrikula;
		this.driver = pDriver;
		this.plazak = pPlazak;
		this.marka = pMarka;
	}

	public String getMatrikula() {
		return matrikula;
	}

	public void setMatrikula(String matrikula) {
		this.matrikula = matrikula;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public int getPlazak() {
		return plazak;
	}

	public void setPlazak(int plazak) {
		this.plazak = plazak;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	@Override
	public String toString() {
		return this.marka+"-"+this.matrikula;
	}
	
	
}
