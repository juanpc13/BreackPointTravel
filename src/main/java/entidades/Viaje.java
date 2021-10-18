/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jcpleitez
 */
@Entity
@Table(name = "viaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Viaje.findAll", query = "SELECT v FROM Viaje v"),
    @NamedQuery(name = "Viaje.findByIdViaje", query = "SELECT v FROM Viaje v WHERE v.idViaje = :idViaje"),
    @NamedQuery(name = "Viaje.findByFechaViaje", query = "SELECT v FROM Viaje v WHERE v.fechaViaje = :fechaViaje"),
    @NamedQuery(name = "Viaje.findByPrecio", query = "SELECT v FROM Viaje v WHERE v.precio = :precio")})
public class Viaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id_viaje")
    private Integer idViaje;
    @Basic(optional = false)
    @Lob
    @Column(name = "nombre_viaje")
    private String nombreViaje;
    @Basic(optional = false)
    @Lob
    @Column(name = "nombre_destino")
    private String nombreDestino;
    @Basic(optional = false)
    @Column(name = "fecha_viaje")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaViaje;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "precio")
    private BigDecimal precio;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idViaje")
    private List<Cliente> clienteList;

    public Viaje() {
    }

    public Viaje(Integer idViaje) {
        this.idViaje = idViaje;
    }

    public Viaje(Integer idViaje, String nombreViaje, String nombreDestino, Date fechaViaje, BigDecimal precio) {
        this.idViaje = idViaje;
        this.nombreViaje = nombreViaje;
        this.nombreDestino = nombreDestino;
        this.fechaViaje = fechaViaje;
        this.precio = precio;
    }

    public Integer getIdViaje() {
        return idViaje;
    }

    public void setIdViaje(Integer idViaje) {
        this.idViaje = idViaje;
    }

    public String getNombreViaje() {
        return nombreViaje;
    }

    public void setNombreViaje(String nombreViaje) {
        this.nombreViaje = nombreViaje;
    }

    public String getNombreDestino() {
        return nombreDestino;
    }

    public void setNombreDestino(String nombreDestino) {
        this.nombreDestino = nombreDestino;
    }

    public Date getFechaViaje() {
        return fechaViaje;
    }

    public void setFechaViaje(Date fechaViaje) {
        this.fechaViaje = fechaViaje;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    @XmlTransient
    public List<Cliente> getClienteList() {
        return clienteList;
    }

    public void setClienteList(List<Cliente> clienteList) {
        this.clienteList = clienteList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idViaje != null ? idViaje.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Viaje)) {
            return false;
        }
        Viaje other = (Viaje) object;
        if ((this.idViaje == null && other.idViaje != null) || (this.idViaje != null && !this.idViaje.equals(other.idViaje))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Viaje[ idViaje=" + idViaje + " ]";
    }
    
}
