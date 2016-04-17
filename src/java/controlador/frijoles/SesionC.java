/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.frijoles;

import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import controlador.logica.SesionL;
import modelo.Pasajero;




/**
 *
 * @author diana
 */
@Named(value = "sesionC")

@ManagedBean
public class SesionC implements Serializable {

   private Pasajero p = new Pasajero();

    public Pasajero getPasajero() {
        return p;
    }

    public void setPasajero(Pasajero p) {
        this.p = p;
    }

    public String verificarDatos() throws Exception {
        SesionL sl = new SesionL();
        Pasajero pa;
        String resultado;

        try {
            pa = sl.verificarDatos(this.p);
            if (pa != null) {
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("pasajero", pa);
                resultado = "inicio";
            } else {
                resultado = "error";
            }
        } catch (Exception e) {
            throw e;
        }

        return resultado;
    }

    public boolean verificarSesion() {
        boolean estado;
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("pasajero") == null) {
            estado = false;
        } else {
            estado = true;
        }

        return estado;
    }

    public String cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        return "index";
    }
        
}
