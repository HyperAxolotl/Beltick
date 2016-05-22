/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.frijoles;

import controlador.logica.ServiciosRealizadosL;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import modelo.Chofer;
import modelo.Pasajero;

/**
 *
 * @author diana
 */
@Named(value = "serviciosRealizadosC")
@Dependent
public class ServiciosRealizadosC {

    private Chofer chofer = new Chofer();
    private Pasajero pasajero = new Pasajero();
    private List<Pasajero> lpasajeros;
    
    private final ServiciosRealizadosL ayudante;

    /**
     * Creates a new instance of ServiciosRealizadosC
     */
    public ServiciosRealizadosC() {
        ayudante = new ServiciosRealizadosL();

    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

   

    public List<Pasajero> servicio(Chofer chofer) {
       lpasajeros = ayudante.lista(chofer);
        
       return lpasajeros;
    }

}
