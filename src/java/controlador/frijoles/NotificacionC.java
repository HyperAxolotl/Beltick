package controlador.frijoles;

import controlador.logica.NotificacionL;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Chofer;
import modelo.NotificacionChofer;
import modelo.NotificacionPasajero;
import modelo.Pasajero;

@Named(value = "notificacionC")
@ManagedBean
@ViewScoped
public class NotificacionC implements Serializable {

    private List<NotificacionChofer> lstNotificacionesC;
    private List<NotificacionPasajero> lstNotificacionesP;
    private NotificacionL ayudante;
    private Chofer chofer;
    private Pasajero pasajero;
    
    public NotificacionC() {
        ayudante = new NotificacionL();
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario").getClass().getSimpleName().equals("Chofer")) {
            chofer = (Chofer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listarChofer();
        } else {
            pasajero = (Pasajero) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listarPasajero();
        }
    }

    public List<NotificacionChofer> getLstNotificacionesC() {
        return lstNotificacionesC;
    }

    public void setLstNotificacionesC(List<NotificacionChofer> lstNotificacionesC) {
        this.lstNotificacionesC = lstNotificacionesC;
    }

    public List<NotificacionPasajero> getLstNotificacionesP() {
        return lstNotificacionesP;
    }

    public void setLstNotificacionesP(List<NotificacionPasajero> lstNotificacionesP) {
        this.lstNotificacionesP = lstNotificacionesP;
    }
    
    public void listarChofer() {
        lstNotificacionesC = ayudante.listarChofer(chofer);
    }
    
    public void listarPasajero() {
        lstNotificacionesP = ayudante.listarPasajero(pasajero);
    }
}
