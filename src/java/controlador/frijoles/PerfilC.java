/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.frijoles;

import controlador.logica.PerfilL;
import modelo.PerfilPasajero;
import modelo.PerfilChofer;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author hyperaxolotl
 */
@ManagedBean
@SessionScoped
public class PerfilC {
    private List<PerfilPasajero> pasajeros;
    private List<PerfilChofer> choferes;
    private final PerfilL ayudante = new PerfilL();
    private boolean mostrarPasajeros;
    private boolean mostrarChoferes;
    private PerfilChofer perfilChofer;
    /**
     * Creates a new instance of PerfilC
     */
    public PerfilC() {
    }

    public PerfilChofer getPerfilChofer() {
        return perfilChofer;
    }
    
    public boolean isMostrarPasajeros() {
        return mostrarPasajeros;
    }

    public boolean isMostrarChoferes() {
        return mostrarChoferes;
    }

    public void setMostrarPasajeros(boolean mostrarPasajeros) {
        this.mostrarPasajeros = mostrarPasajeros;
    }

    public void setMostrarChoferes(boolean mostrarChoferes) {
        this.mostrarChoferes = mostrarChoferes;
    }
    
    
    public List<PerfilChofer> getChoferes() {
        return choferes;
    }

    public List<PerfilPasajero> getPasajeros() {
        return pasajeros;
    }

    public void setChoferes(List<PerfilChofer> choferes) {
        this.choferes = choferes;
    }

    public void setPasajeros(List<PerfilPasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }
    
    public void listarChoferes(){
        mostrarPasajeros = false;
        choferes = ayudante.listaChoferes();
        mostrarChoferes = true;
    }
    
    public void listarPasajeros(){
        mostrarChoferes = false;
        pasajeros = ayudante.listaPasajeros();
        mostrarPasajeros = true;
    }
    
    public String muestraCPerfil(PerfilChofer perfil){
        perfilChofer = perfil;
        mostrarChoferes = mostrarPasajeros = false;
        return "PerfilIH";
    }
    
    
    
}
