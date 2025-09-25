package domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Movement {

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@Id
	private int id;
	private int mugimenduZenbakia;
	private double dirua;
	private boolean sarreraDa;
	private Date data;
	
	@ManyToOne
    private User user;
	
	public Movement(int id, double cantidad, boolean esEntrada, Date fecha) {
		this.mugimenduZenbakia = id ;
        this.dirua = cantidad;
        this.sarreraDa = esEntrada;
        this.data = fecha;
    }
	
	public int getMugimenduZenbakia() {
        return mugimenduZenbakia;
    }

    public void setMugimenduZenbakia(int id) {
        this.mugimenduZenbakia = id;
    }

    public double getDirua() {
        return dirua;
    }

    public void setDirua(double cantidad) {
        this.dirua = cantidad;
    }

    public boolean getSarreraDa() {
        return sarreraDa;
    }

    public void setSarreraDa(boolean esEntrada) {
        this.sarreraDa = esEntrada;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date fecha) {
        this.data = fecha;
    }


}
