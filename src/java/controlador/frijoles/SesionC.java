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

    private Pasajero p = new Pasajero();
    private Chofer c = new Chofer();
    private boolean tipo;
    private Horario h = new Horario();
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
        if (tipo == false) {
            Pasajero pa;
            try {
                pa = sl.verificarDatos(this.p);
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
                usc = sl.verificarDatos(this.c);
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
            pc = (PerfilChofer) c.getPerfilChofers().iterator().next();
        } else {
            pp = (PerfilPasajero) p.getPerfilPasajeros().iterator().next();
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
        Chofer tmp = (Chofer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return (Automovil) tmp.getAutomovils().iterator().next();
    }

    public boolean tieneAuto() {
        Chofer tmp = (Chofer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return tmp.getAutomovils().size() > 0;
    }

}
