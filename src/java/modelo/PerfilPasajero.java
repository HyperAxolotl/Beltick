package modelo;
// Generated May 16, 2016 1:45:53 AM by Hibernate Tools 4.3.1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * PerfilPasajero generated by hbm2java
 */
@Entity
@Table(name = "perfil_pasajero", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = "clave"),
    @UniqueConstraint(columnNames = "id_pasajero")}
)
public class PerfilPasajero implements java.io.Serializable {

    private int idPpasajero;
    private Imagen imagen;
    private Pasajero pasajero;
    private String psobreMi;
    private Boolean pestado;
    private Date fechaCreacion;
    private String clave;

    public PerfilPasajero() {
    }

    public PerfilPasajero(int idPpasajero, Pasajero pasajero, Date fechaCreacion) {
        this.idPpasajero = idPpasajero;
        this.pasajero = pasajero;
        this.fechaCreacion = fechaCreacion;
    }

    public PerfilPasajero(int idPpasajero, Imagen imagen, Pasajero pasajero, String psobreMi, Boolean pestado, Date fechaCreacion, String clave) {
        this.idPpasajero = idPpasajero;
        this.imagen = imagen;
        this.pasajero = pasajero;
        this.psobreMi = psobreMi;
        this.pestado = pestado;
        this.fechaCreacion = fechaCreacion;
        this.clave = clave;
    }

    @Id

    @Column(name = "id_ppasajero", unique = true, nullable = false)
    public int getIdPpasajero() {
        return this.idPpasajero;
    }

    public void setIdPpasajero(int idPpasajero) {
        this.idPpasajero = idPpasajero;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pfoto")
    public Imagen getImagen() {
        return this.imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pasajero", unique = true, nullable = false)
    public Pasajero getPasajero() {
        return this.pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    @Column(name = "psobre_mi", length = 256)
    public String getPsobreMi() {
        return this.psobreMi;
    }

    public void setPsobreMi(String psobreMi) {
        this.psobreMi = psobreMi;
    }

    @Column(name = "pestado")
    public Boolean getPestado() {
        return this.pestado;
    }

    public void setPestado(Boolean pestado) {
        this.pestado = pestado;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_creacion", nullable = false, length = 29)
    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Column(name = "clave", unique = true, length = 32)
    public String getClave() {
        return this.clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

}
