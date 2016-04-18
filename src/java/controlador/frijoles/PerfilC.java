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
import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author hyperaxolotl
 */
@ManagedBean
@ViewScoped
public class PerfilC {
    private List<PerfilPasajero> pasajeros;
    private List<PerfilChofer> choferes;
    private final PerfilL ayudante = new PerfilL();
    /**
     * Creates a new instance of PerfilC
     */
    public PerfilC() {
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
        choferes = ayudante.listaChoferes();
    }
    
    public void listarPasajeros(){
        pasajeros = ayudante.listaPasajeros();
    }
    
    
    
    
}
