package modelo;
// Generated 10/04/2016 10:54:38 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Chofer generated by hbm2java
 */
public class Chofer  implements java.io.Serializable {


     private int idChofer;
     private String cnombre;
     private String capp;
     private String capm;
     private String cnoCuenta;
     private String cnoId;
     private Date cfechaNac;
     private String ccontrasenia;
     private Set perfilChofers = new HashSet(0);
     private Set automovils = new HashSet(0);

    public Chofer() {
    }

	
    public Chofer(int idChofer, String cnombre, String capp, String capm, String cnoCuenta, String cnoId, Date cfechaNac, String ccontrasenia) {
        this.idChofer = idChofer;
        this.cnombre = cnombre;
        this.capp = capp;
        this.capm = capm;
        this.cnoCuenta = cnoCuenta;
        this.cnoId = cnoId;
        this.cfechaNac = cfechaNac;
        this.ccontrasenia = ccontrasenia;
    }
    public Chofer(int idChofer, String cnombre, String capp, String capm, String cnoCuenta, String cnoId, Date cfechaNac, String ccontrasenia, Set perfilChofers, Set automovils) {
       this.idChofer = idChofer;
       this.cnombre = cnombre;
       this.capp = capp;
       this.capm = capm;
       this.cnoCuenta = cnoCuenta;
       this.cnoId = cnoId;
       this.cfechaNac = cfechaNac;
       this.ccontrasenia = ccontrasenia;
       this.perfilChofers = perfilChofers;
       this.automovils = automovils;
    }
   
    public int getIdChofer() {
        return this.idChofer;
    }
    
    public void setIdChofer(int idChofer) {
        this.idChofer = idChofer;
    }
    public String getCnombre() {
        return this.cnombre;
    }
    
    public void setCnombre(String cnombre) {
        this.cnombre = cnombre;
    }
    public String getCapp() {
        return this.capp;
    }
    
    public void setCapp(String capp) {
        this.capp = capp;
    }
    public String getCapm() {
        return this.capm;
    }
    
    public void setCapm(String capm) {
        this.capm = capm;
    }
    public String getCnoCuenta() {
        return this.cnoCuenta;
    }
    
    public void setCnoCuenta(String cnoCuenta) {
        this.cnoCuenta = cnoCuenta;
    }
    public String getCnoId() {
        return this.cnoId;
    }
    
    public void setCnoId(String cnoId) {
        this.cnoId = cnoId;
    }
    public Date getCfechaNac() {
        return this.cfechaNac;
    }
    
    public void setCfechaNac(Date cfechaNac) {
        this.cfechaNac = cfechaNac;
    }
    public String getCcontrasenia() {
        return this.ccontrasenia;
    }
    
    public void setCcontrasenia(String ccontrasenia) {
        this.ccontrasenia = ccontrasenia;
    }
    public Set getPerfilChofers() {
        return this.perfilChofers;
    }
    
    public void setPerfilChofers(Set perfilChofers) {
        this.perfilChofers = perfilChofers;
    }
    public Set getAutomovils() {
        return this.automovils;
    }
    
    public void setAutomovils(Set automovils) {
        this.automovils = automovils;
    }




}

