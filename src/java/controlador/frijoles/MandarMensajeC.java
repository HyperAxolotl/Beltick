package controlador.frijoles;

import controlador.logica.MensajeL;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Chofer;
import modelo.Horario;
import modelo.MensajeChofer;
import modelo.MensajePasajero;
import modelo.Pasajero;

@Named(value = "mandarMensajeC")
@ManagedBean
@ViewScoped
public class MandarMensajeC implements Serializable {

    private MensajeChofer mensajeC;
    private MensajePasajero mensajeP;
    private MensajeL ayudante;
    private FacesMessage mensaje;
    private Chofer chofer;
    private Pasajero pasajero;
    private boolean exito;

    public MandarMensajeC() {
        mensajeC = new MensajeChofer();
        mensajeP = new MensajePasajero();
        ayudante = new MensajeL();
    }
    
    public boolean isExito() {
        return exito;
    }

    public MensajeChofer getMensajeC() {
        return mensajeC;
    }

    public void setMensajeC(MensajeChofer mensajeC) {
        this.mensajeC = mensajeC;
    }

    public MensajePasajero getMensajeP() {
        return mensajeP;
    }

    public void setMensajeP(MensajePasajero mensajeP) {
        this.mensajeP = mensajeP;
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

    public String registroMensajeC(Pasajero p) {
        mensaje = ayudante.registroMensajeC(mensajeC, p, chofer);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tu mensaje se envio exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        exito = true;
        return "";
    }

    public String registroMensajeP(Chofer c) {
        mensaje = ayudante.registroMensajeP(mensajeP, c, pasajero);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tu mensaje se envio exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        exito = true;
        return "";
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario").getClass().getSimpleName().equals("Pasajero")) {
            chofer = ayudante.getChofer(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("choferId")));
        } else {
            pasajero = ayudante.getPasajero(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pasajeroId")));
        }
    }

}
