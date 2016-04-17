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
import modelo.Chofer;
import modelo.Pasajero;




/**
 *
 * @author diana
 */
@Named(value = "sesionC")

@ManagedBean
public class SesionC implements Serializable {

   private Pasajero p = new Pasajero();
   private Chofer c = new Chofer();
   private boolean tipo;

    public Chofer getChofer() {
        return c;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setChofer(Chofer c) {
        this.c = c;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }
   
   

    public Pasajero getPasajero() {
        return p;
    }

    public void setPasajero(Pasajero p) {
        this.p = p;
    }

    public String verificarDatos() throws Exception {
        SesionL sl = new SesionL();
        String resultado;
        if(tipo == false) {
            Pasajero pa;
            try {
            pa = sl.verificarDatos(this.p);
            if (pa != null) {
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("usuario", pa);
                resultado = "inicioPasajero";
            } else 
                resultado = "error";
            } catch (Exception e) {
            throw e;
            }
        } else {    
            Chofer usc;
            try {
            usc = sl.verificarDatos(this.c);
            if (usc != null) {
                FacesContext.getCurrentInstance().getExternalContext()
                        .getSessionMap().put("usuario", usc);
                resultado = "inicioChofer";
            } else 
                resultado = "error";
            } catch (Exception e) {
            throw e;
            }
        }
        return resultado;
    }

    public boolean verificarSesion() {
        boolean estado;
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("usuario") == null) {
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
