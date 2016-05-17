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
 * CalificacionChofer generated by hbm2java
 */
@Entity
@Table(name = "calificacion_chofer", schema = "public", uniqueConstraints = @UniqueConstraint(columnNames = {"id_pasajero", "id_chofer"})
)
public class CalificacionChofer implements java.io.Serializable {

    private int idCalificacionc;
    private Chofer chofer;
    private Pasajero pasajero;
    private int calificacion;
    private String descripcion;
    private Date fecha;

    public CalificacionChofer() {
    }

    public CalificacionChofer(int idCalificacionc, Chofer chofer, Pasajero pasajero, int calificacion, Date fecha) {
        this.idCalificacionc = idCalificacionc;
        this.chofer = chofer;
        this.pasajero = pasajero;
        this.calificacion = calificacion;
        this.fecha = fecha;
    }

    public CalificacionChofer(int idCalificacionc, Chofer chofer, Pasajero pasajero, int calificacion, String descripcion, Date fecha) {
        this.idCalificacionc = idCalificacionc;
        this.chofer = chofer;
        this.pasajero = pasajero;
        this.calificacion = calificacion;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    @Id

    @Column(name = "id_calificacionc", unique = true, nullable = false)
    public int getIdCalificacionc() {
        return this.idCalificacionc;
    }

    public void setIdCalificacionc(int idCalificacionc) {
        this.idCalificacionc = idCalificacionc;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_chofer", nullable = false)
    public Chofer getChofer() {
        return this.chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pasajero", nullable = false)
    public Pasajero getPasajero() {
        return this.pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    @Column(name = "calificacion", nullable = false)
    public int getCalificacion() {
        return this.calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    @Column(name = "descripcion", length = 256)
    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha", nullable = false, length = 29)
    public Date getFecha() {
        return this.fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

}
