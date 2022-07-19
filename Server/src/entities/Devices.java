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
@Table(name = "devices")
@NamedQueries({
    @NamedQuery(name = "Devices.findAll", query = "SELECT d FROM Devices d"),
    @NamedQuery(name = "Devices.findById", query = "SELECT d FROM Devices d WHERE d.id = :id"),
    @NamedQuery(name = "Devices.findByIdentifier", query = "SELECT d FROM Devices d WHERE d.identifier = :identifier"),
    @NamedQuery(name = "Devices.findByDate", query = "SELECT d FROM Devices d WHERE d.date = :date"),
    @NamedQuery(name = "Devices.findByTemperature", query = "SELECT d FROM Devices d WHERE d.temperature = :temperature"),
    @NamedQuery(name = "Devices.findByHumidity", query = "SELECT d FROM Devices d WHERE d.humidity = :humidity"),
    @NamedQuery(name = "Devices.findByConnected", query = "SELECT d FROM Devices d WHERE d.connected = :connected")})
public class Devices implements Serializable {

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
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @Column(name = "temperature")
    private float temperature;
    @Basic(optional = false)
    @Column(name = "humidity")
    private float humidity;
    @Basic(optional = false)
    @Column(name = "connected")
    private boolean connected;

    public Devices() {
    }

    public Devices(Integer id) {
        this.id = id;
    }

    public Devices(Integer id, String identifier, Date date, float temperature, float humidity, boolean connected) {
        this.id = id;
        this.identifier = identifier;
        this.date = date;
        this.temperature = temperature;
        this.humidity = humidity;
        this.connected = connected;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public boolean getConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
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
        if (!(object instanceof Devices)) {
            return false;
        }
        Devices other = (Devices) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Devices[ id=" + id + " ]";
    }
    
}
