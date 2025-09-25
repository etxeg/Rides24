package domain;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Alert {
	
	private static final long serialVersionUID = 1L;
	@XmlID
	@GeneratedValue
	@Id
	private int id;
	private User user;
	private String nundik;
	private String nora;
	private Date data;
	
	public Alert(User u, String nundik, String nora, Date d) {
		this.user=u;
		this.nundik= nundik;
		this.nora=nora;
		this.data=d;
	}

	public String getNundik() {
		return nundik;
	}

	public void setNundik(String nundik) {
		this.nundik = nundik;
	}

	public String getNora() {
		return nora;
	}

	public void setNora(String nora) {
		this.nora = nora;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "Alert [nundik=" + nundik + ", nora=" + nora + ", data=" + data + "]";
	}
}
