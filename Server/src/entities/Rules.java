/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "rules")
@NamedQueries({
    @NamedQuery(name = "Rules.findAll", query = "SELECT r FROM Rules r"),
    @NamedQuery(name = "Rules.findById", query = "SELECT r FROM Rules r WHERE r.id = :id"),
    @NamedQuery(name = "Rules.findByRi", query = "SELECT r FROM Rules r WHERE r.ri = :ri"),
    @NamedQuery(name = "Rules.findByRf", query = "SELECT r FROM Rules r WHERE r.rf = :rf"),
    @NamedQuery(name = "Rules.findByMessage", query = "SELECT r FROM Rules r WHERE r.message = :message"),
    @NamedQuery(name = "Rules.findByMail", query = "SELECT r FROM Rules r WHERE r.mail = :mail")})
public class Rules implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "ri")
    private float ri;
    @Basic(optional = false)
    @Column(name = "rf")
    private float rf;
    @Basic(optional = false)
    @Column(name = "message")
    private String message;
    @Basic(optional = false)
    @Column(name = "mail")
    private String mail;

    public Rules() {
    }

    public Rules(Integer id) {
        this.id = id;
    }

    public Rules(Integer id, float ri, float rf, String message, String mail) {
        this.id = id;
        this.ri = ri;
        this.rf = rf;
        this.message = message;
        this.mail = mail;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public float getRi() {
        return ri;
    }

    public void setRi(float ri) {
        this.ri = ri;
    }

    public float getRf() {
        return rf;
    }

    public void setRf(float rf) {
        this.rf = rf;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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
        if (!(object instanceof Rules)) {
            return false;
        }
        Rules other = (Rules) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Rules[ id=" + id + " ]";
    }
    
}
