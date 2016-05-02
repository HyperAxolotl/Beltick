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
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import modelo.Chofer;
import modelo.Pasajero;
import modelo.PerfilChofer;
import modelo.PerfilPasajero;




/**
 *
 * @author diana
 */
@Named(value = "sesionC")
@SessionScoped
@ManagedBean
public class SesionC implements Serializable {

   private Pasajero p = new Pasajero();
   private Chofer c = new Chofer();
   private boolean tipo;
   private FacesMessage mensaje;
   private PerfilChofer pc;
   private PerfilPasajero pp;

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
                p = pa;
            } else {
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo o contraseña incorrectos", null);
                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                resultado = "";
            }
           
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
                c = usc;
            } else {
                mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correo o contraseña incorrectos", null);
                FacesContext.getCurrentInstance().addMessage(null, mensaje);
                resultado = "";
            }
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
        /* is all of this necessary?*/
        pp = null;
        pc = null;
        c = null;
        p = null;
        return "PaginaPrincipalIH";
    }
    
    /**
     * True si es chofer, False en caso contrario
     * 
     * @return 
     */
    public boolean verificarTipo() {
        String tipo = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario").getClass().getSimpleName();
        if(tipo.equals("Chofer"))
            return true;
        return false;
    }
    
    public String verPerfil(){
        if(verificarTipo())
            return "inicioChofer";
        return "inicioPasajero";
    }
    
    private void getPerfil(){
        if(verificarTipo())
            pc = (PerfilChofer)c.getPerfilChofers().iterator().next();
        else
            pp = (PerfilPasajero)p.getPerfilPasajeros().iterator().next();
    }
    
    public String rutaIMG(){
        getPerfil();
        if(pc != null) {
            return pc.getCfoto();
        }
        if(pp != null)
            return pp.getPfoto();
        return "";
    }
    
    public String descripcion(){
        getPerfil();
        if(pc != null) {
            return pc.getCsobreMi();
        }
        if(pp != null)
            return pp.getPsobreMi();
        return "";
    }
    
    
    
    
        
}
