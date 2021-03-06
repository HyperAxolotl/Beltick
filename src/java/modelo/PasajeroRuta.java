package modelo;
// Generated May 7, 2016 1:06:44 AM by Hibernate Tools 4.3.1

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * PasajeroRuta generated by hbm2java
 */
@Entity
@Table(name = "pasajero_ruta", schema = "public"
)
public class PasajeroRuta implements java.io.Serializable {

    private PasajeroRutaId id;
    private Pasajero pasajero;
    private Ruta ruta;

    public PasajeroRuta() {
    }

    public PasajeroRuta(PasajeroRutaId id, Pasajero pasajero, Ruta ruta) {
        this.id = id;
        this.pasajero = pasajero;
        this.ruta = ruta;
    }

    @EmbeddedId

    @AttributeOverrides({
        @AttributeOverride(name = "idPasajero", column = @Column(name = "id_pasajero", nullable = false)),
        @AttributeOverride(name = "idRuta", column = @Column(name = "id_ruta", nullable = false)),
        @AttributeOverride(name = "dia", column = @Column(name = "dia", nullable = false, length = 16))})
    public PasajeroRutaId getId() {
        return this.id;
    }

    public void setId(PasajeroRutaId id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pasajero", nullable = false, insertable = false, updatable = false)
    public Pasajero getPasajero() {
        return this.pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ruta", nullable = false, insertable = false, updatable = false)
    public Ruta getRuta() {
        return this.ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

}
