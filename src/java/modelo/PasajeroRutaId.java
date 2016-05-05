package modelo;
// Generated May 4, 2016 9:25:30 PM by Hibernate Tools 4.3.1

/**
 * PasajeroRutaId generated by hbm2java
 */
public class PasajeroRutaId implements java.io.Serializable {

    private int idPasajero;
    private int idRuta;
    private String dia;

    public PasajeroRutaId() {
    }

    public PasajeroRutaId(int idPasajero, int idRuta, String dia) {
        this.idPasajero = idPasajero;
        this.idRuta = idRuta;
        this.dia = dia;
    }

    public int getIdPasajero() {
        return this.idPasajero;
    }

    public void setIdPasajero(int idPasajero) {
        this.idPasajero = idPasajero;
    }

    public int getIdRuta() {
        return this.idRuta;
    }

    public void setIdRuta(int idRuta) {
        this.idRuta = idRuta;
    }

    public String getDia() {
        return this.dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public boolean equals(Object other) {
        if ((this == other)) {
            return true;
        }
        if ((other == null)) {
            return false;
        }
        if (!(other instanceof PasajeroRutaId)) {
            return false;
        }
        PasajeroRutaId castOther = (PasajeroRutaId) other;

        return (this.getIdPasajero() == castOther.getIdPasajero())
                && (this.getIdRuta() == castOther.getIdRuta())
                && ((this.getDia() == castOther.getDia()) || (this.getDia() != null && castOther.getDia() != null && this.getDia().equals(castOther.getDia())));
    }

    public int hashCode() {
        int result = 17;

        result = 37 * result + this.getIdPasajero();
        result = 37 * result + this.getIdRuta();
        result = 37 * result + (getDia() == null ? 0 : this.getDia().hashCode());
        return result;
    }

}
