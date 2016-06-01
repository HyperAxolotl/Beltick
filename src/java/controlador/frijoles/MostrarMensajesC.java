package controlador.frijoles;

import controlador.logica.HorarioL;
import controlador.logica.MensajeL;
import controlador.logica.NotificacionL;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Chofer;
import modelo.MensajeChofer;
import modelo.MensajePasajero;
import modelo.Pasajero;

@Named(value = "mostrarMensajesC")
@ManagedBean
@ViewScoped
public class MostrarMensajesC implements Serializable {

    private List<MensajeChofer> lstMensajesC;
    private List<MensajePasajero> lstMensajesP;
    private MensajeL ayudante;
    private Chofer chofer;
    private Pasajero pasajero;

    public MostrarMensajesC() {
        ayudante = new MensajeL();
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario").getClass().getSimpleName().equals("Chofer")) {
            chofer = (Chofer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listarChofer();
        } else {
            pasajero = (Pasajero) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listarPasajero();
        }
    }

    public List<MensajeChofer> getLstMensajesC() {
        return lstMensajesC;
    }

    public void setLstMensajesC(List<MensajeChofer> lstMensajesC) {
        this.lstMensajesC = lstMensajesC;
    }

    public List<MensajePasajero> getLstMensajesP() {
        return lstMensajesP;
    }

    public void setLstMensajesP(List<MensajePasajero> lstMensajesP) {
        this.lstMensajesP = lstMensajesP;
    }

    public void listarChofer() {
        lstMensajesC = ayudante.listarChofer(chofer);
    }

    public void listarPasajero() {
        lstMensajesP = ayudante.listarPasajero(pasajero);
    }
    
    public String formateaFecha(Date fecha) {
        HorarioL h = new HorarioL();
        return h.formateaFecha(fecha);
    }

}
