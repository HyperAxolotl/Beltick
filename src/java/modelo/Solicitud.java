package modelo;
// Generated Apr 30, 2016 2:19:16 AM by Hibernate Tools 4.3.1



/**
 * Solicitud generated by hbm2java
 */
public class Solicitud  implements java.io.Serializable {


     private SolicitudId id;
     private Pasajero pasajero;
     private Ruta ruta;

    public Solicitud() {
    }

    public Solicitud(SolicitudId id, Pasajero pasajero, Ruta ruta) {
       this.id = id;
       this.pasajero = pasajero;
       this.ruta = ruta;
    }
   
    public SolicitudId getId() {
        return this.id;
    }
    
    public void setId(SolicitudId id) {
        this.id = id;
    }
    public Pasajero getPasajero() {
        return this.pasajero;
    }
    
    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    public Ruta getRuta() {
        return this.ruta;
    }
    
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }




}


