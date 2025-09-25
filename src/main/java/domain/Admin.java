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
public class Admin extends User implements Serializable  {
	
	/**
	 * 
	 */
	
	//@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	private List<Reclamation> reclam=new Vector<Reclamation>();

	public Admin() {
		super();
	}

	public Admin(String email, String name,String password) {
		super(email,name,password);
	}

	public List<Reclamation> getReclam() {
		return reclam;
	}

	public void setReclam(List<Reclamation> reclam) {
		this.reclam = reclam;
	}
	

	
	
}
