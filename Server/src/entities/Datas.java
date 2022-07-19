/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "datas")
@NamedQueries({
    @NamedQuery(name = "Datas.findAll", query = "SELECT d FROM Datas d"),
    @NamedQuery(name = "Datas.findById", query = "SELECT d FROM Datas d WHERE d.id = :id"),
    @NamedQuery(name = "Datas.findByIdentifier", query = "SELECT d FROM Datas d WHERE d.identifier = :identifier"),
    @NamedQuery(name = "Datas.findByTemperature", query = "SELECT d FROM Datas d WHERE d.temperature = :temperature"),
    @NamedQuery(name = "Datas.findByHumidity", query = "SELECT d FROM Datas d WHERE d.humidity = :humidity"),
    @NamedQuery(name = "Datas.findByDate", query = "SELECT d FROM Datas d WHERE d.date = :date")})
public class Datas implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "identifier")
    private String identifier;
    @Basic(optional = false)
    @Column(name = "temperature")
    private float temperature;
    @Basic(optional = false)
    @Column(name = "humidity")
    private float humidity;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Datas() {
    }

    public Datas(Integer id) {
        this.id = id;
    }

    public Datas(Integer id, String identifier, float temperature, float humidity, Date date) {
        this.id = id;
        this.identifier = identifier;
        this.temperature = temperature;
        this.humidity = humidity;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public void setHumidity(float humidity) {
        this.humidity = humidity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datas)) {
            return false;
        }
        Datas other = (Datas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Datas[ id=" + id + " ]";
    }
    
}
