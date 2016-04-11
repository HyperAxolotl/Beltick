package modelo;
// Generated 10/04/2016 10:54:38 PM by Hibernate Tools 4.3.1



/**
 * PerfilPasajero generated by hbm2java
 */
public class PerfilPasajero  implements java.io.Serializable {


     private int idPpasajero;
     private Pasajero pasajero;
     private String foto;
     private String sobreMi;
     private Boolean estado;

    public PerfilPasajero() {
    }

	
    public PerfilPasajero(int idPpasajero, Pasajero pasajero) {
        this.idPpasajero = idPpasajero;
        this.pasajero = pasajero;
    }
    public PerfilPasajero(int idPpasajero, Pasajero pasajero, String foto, String sobreMi, Boolean estado) {
       this.idPpasajero = idPpasajero;
       this.pasajero = pasajero;
       this.foto = foto;
       this.sobreMi = sobreMi;
       this.estado = estado;
    }
   
    public int getIdPpasajero() {
        return this.idPpasajero;
    }
    
    public void setIdPpasajero(int idPpasajero) {
        this.idPpasajero = idPpasajero;
    }
    public Pasajero getPasajero() {
        return this.pasajero;
    }
    
    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    public String getFoto() {
        return this.foto;
    }
    
    public void setFoto(String foto) {
        this.foto = foto;
    }
    public String getSobreMi() {
        return this.sobreMi;
    }
    
    public void setSobreMi(String sobreMi) {
        this.sobreMi = sobreMi;
    }
    public Boolean getEstado() {
        return this.estado;
    }
    
    public void setEstado(Boolean estado) {
        this.estado = estado;
    }




}


