/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.frijoles;

import controlador.logica.PerfilL;
import java.util.Iterator;
import modelo.PerfilPasajero;
import modelo.PerfilChofer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
//import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
//import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import modelo.Automovil;
import modelo.Ruta;

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
    private boolean tipo;
    private PerfilChofer perfilChofer;
    private PerfilPasajero perfilPasajero;

    /**
     * Creates a new instance of PerfilC
     */
    public PerfilC() {
    }

    public boolean isTipo() {
        return tipo;
    }

    public PerfilPasajero getPerfilPasajero() {
        return perfilPasajero;
    }

    public PerfilChofer getPerfilChofer() {
        return perfilChofer;
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

    @PostConstruct
    public void listar() {
        choferes = ayudante.listaChoferes();
        pasajeros = ayudante.listaPasajeros();
    }

    public void listarChoferes() {
        choferes = ayudante.listaChoferes();
    }

    public void listarPasajeros() {
        pasajeros = ayudante.listaPasajeros();
    }

    public String muestraPerfil(Object perfil, boolean tipo) {
        this.tipo = tipo;
        if (tipo) {
            perfilChofer = (PerfilChofer) perfil;
            return "PerfilIH?faces-redirect=true";
        }
        perfilPasajero = (PerfilPasajero) perfil;
        return "PerfilIH?faces-redirect=true";
    }
    
    public int getIdRuta() {
        Automovil tmp;
        Iterator<Automovil> ia = (Iterator<Automovil>) perfilChofer.getChofer().getAutomovils().iterator();
        if (ia.hasNext()) {
            tmp = (Automovil) ia.next();
            Iterator<Ruta> ir = (Iterator<Ruta>) tmp.getRutas().iterator();
            if (ir.hasNext()) {
                return ((Ruta) tmp.getRutas().iterator().next()).getIdRuta();
            }
        }
        return -100;
    }

}
