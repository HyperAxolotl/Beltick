/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.frijoles;

import controlador.logica.NotificacionL;
import controlador.logica.PerfilChoferL;
import controlador.logica.PerfilPasajeroL;
import javax.inject.Named;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import controlador.logica.SesionL;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import modelo.Automovil;
import modelo.Chofer;
import modelo.Pasajero;
import modelo.PerfilChofer;
import modelo.PerfilPasajero;
import modelo.Horario;

/**
 *
 * @author hyperaxolotl
 */
@Named(value = "sesionC")
@SessionScoped
@ManagedBean
public class SesionC implements Serializable {

    private Pasajero p;
    private Chofer c;
    private boolean tipo;
    private Horario h;
    private FacesMessage mensaje;
    private PerfilChofer pc;
    private PerfilPasajero pp;
    private SesionL ayudante;
    private NotificacionL notificacionL;
    private int notificacionesNuevas;

    public SesionC() {
        ayudante = new SesionL();
        notificacionL = new NotificacionL();
        p = new Pasajero();
        c = new Chofer();
        h = new Horario();
    }

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

    public int getNotificacionesNuevas() {
        return notificacionesNuevas;
    }

    public void setNotificacionesNuevas(int notificacionesNuevas) {
        this.notificacionesNuevas = notificacionesNuevas;
    }
    
    public String verificarDatos() throws Exception {
        String resultado;
        if (tipo == false) {
            Pasajero pa;
            try {
                pa = ayudante.verificarDatos(this.p);
                if (pa != null) {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .getSessionMap().put("usuario", pa);
                    resultado = "PerfilPasajeroIH?faces-redirect=true&pasajeroId=" + pa.getIdPasajero();
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
                usc = ayudante.verificarDatos(this.c);
                if (usc != null) {
                    FacesContext.getCurrentInstance().getExternalContext()
                            .getSessionMap().put("usuario", usc);
                    resultado = "PerfilChoferIH?faces-redirect=true&choferId=" + usc.getIdChofer();
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

    public void cerrarSesion() {
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("PaginaPrincipalIH.xhtml");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * True si es chofer, False en caso contrario
     *
     * @return
     */
    public boolean verificarTipo() {
        String tipo = FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario").getClass().getSimpleName();
        if (tipo.equals("Chofer")) {
            return true;
        }
        return false;
    }

    private void getPerfil() {
        if (verificarTipo()) {
            PerfilChoferL pL = new PerfilChoferL();
            pc = pL.getPerfilChofer(c.getIdChofer());
        } else {
            PerfilPasajeroL pL = new PerfilPasajeroL();
            pp = pL.getPerfilPasajero(p.getIdPasajero());
        }
    }

    public String descripcion() {
        getPerfil();
        if (pc != null) {
            return pc.getCsobreMi();
        }
        if (pp != null) {
            return pp.getPsobreMi();
        }
        return "";
    }

    public Automovil cAuto() {
        return ayudante.cAuto(c);
    }

    public boolean tieneAuto() {
        return ayudante.tieneAuto(c);
    }

    public boolean verificarNotificacionesNuevas() {
        if (verificarSesion()) {
            if (verificarTipo()) {
                return ayudante.verificarNotificacionesChofer(c);
            }
            return ayudante.verificarNotificacionesPasajero(p);
        }
        return false;
    }

    public String notificacionesVistas() {
        if (verificarSesion()) {
            System.out.println("NOTIFICACIONES VISTAS");
            if (verificarTipo()) {
                notificacionesNuevas = ayudante.notificacionChoferVista(c);
            } else {
                notificacionesNuevas = ayudante.notificacionPasajeroVista(p);
            }
            return "NotificacionesIH?faces-redirect=true";
        }
        return "";
    }

}
