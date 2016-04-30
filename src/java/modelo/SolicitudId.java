package modelo;
// Generated Apr 30, 2016 2:19:16 AM by Hibernate Tools 4.3.1



/**
 * SolicitudId generated by hbm2java
 */
public class SolicitudId  implements java.io.Serializable {


     private int idPasajero;
     private int idRuta;
     private int dia;

    public SolicitudId() {
    }

    public SolicitudId(int idPasajero, int idRuta, int dia) {
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
    public int getDia() {
        return this.dia;
    }
    
    public void setDia(int dia) {
        this.dia = dia;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof SolicitudId) ) return false;
		 SolicitudId castOther = ( SolicitudId ) other; 
         
		 return (this.getIdPasajero()==castOther.getIdPasajero())
 && (this.getIdRuta()==castOther.getIdRuta())
 && (this.getDia()==castOther.getDia());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getIdPasajero();
         result = 37 * result + this.getIdRuta();
         result = 37 * result + this.getDia();
         return result;
   }   


}


