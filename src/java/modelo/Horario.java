package modelo;
// Generated 10/04/2016 10:54:38 PM by Hibernate Tools 4.3.1



/**
 * Horario generated by hbm2java
 */
public class Horario  implements java.io.Serializable {


     private int idHorario;
     private Ruta ruta;
     private Integer lunes;
     private Integer martes;
     private Integer miercoles;
     private Integer jueves;
     private Integer viernes;
     private Integer sabado;

    public Horario() {
    }

	
    public Horario(int idHorario, Ruta ruta) {
        this.idHorario = idHorario;
        this.ruta = ruta;
    }
    public Horario(int idHorario, Ruta ruta, Integer lunes, Integer martes, Integer miercoles, Integer jueves, Integer viernes, Integer sabado) {
       this.idHorario = idHorario;
       this.ruta = ruta;
       this.lunes = lunes;
       this.martes = martes;
       this.miercoles = miercoles;
       this.jueves = jueves;
       this.viernes = viernes;
       this.sabado = sabado;
    }
   
    public int getIdHorario() {
        return this.idHorario;
    }
    
    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }
    public Ruta getRuta() {
        return this.ruta;
    }
    
    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
    public Integer getLunes() {
        return this.lunes;
    }
    
    public void setLunes(Integer lunes) {
        this.lunes = lunes;
    }
    public Integer getMartes() {
        return this.martes;
    }
    
    public void setMartes(Integer martes) {
        this.martes = martes;
    }
    public Integer getMiercoles() {
        return this.miercoles;
    }
    
    public void setMiercoles(Integer miercoles) {
        this.miercoles = miercoles;
    }
    public Integer getJueves() {
        return this.jueves;
    }
    
    public void setJueves(Integer jueves) {
        this.jueves = jueves;
    }
    public Integer getViernes() {
        return this.viernes;
    }
    
    public void setViernes(Integer viernes) {
        this.viernes = viernes;
    }
    public Integer getSabado() {
        return this.sabado;
    }
    
    public void setSabado(Integer sabado) {
        this.sabado = sabado;
    }




}


