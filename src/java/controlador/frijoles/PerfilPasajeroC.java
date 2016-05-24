package controlador.frijoles;

import controlador.logica.CalificacionL;
import controlador.logica.PerfilL;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.CalificacionChofer;
import modelo.CalificacionPasajero;
import modelo.Chofer;
import modelo.Pasajero;

@Named(value = "perfilPasajeroC")
@ManagedBean
@ViewScoped
public class PerfilPasajeroC {

    private Pasajero pasajero;
    private CalificacionL calificacionL;
    private PerfilL ayudante;
    private CalificacionPasajero calificacionPasajero;
    
    public PerfilPasajeroC() {
        pasajero = new Pasajero();
        ayudante = new PerfilL();
        calificacionL = new CalificacionL();
        calificacionPasajero = new CalificacionPasajero();
    }
    
//    @PostConstruct
    public void init() {
        pasajero = ayudante.getPasajero(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pasajeroId")));
    }

    public CalificacionPasajero getCalificacionPasajero() {
        return calificacionPasajero;
    }

    public void setCalificacionPasajero(CalificacionPasajero calificacionPasajero) {
        this.calificacionPasajero = calificacionPasajero;
    }
    
    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    
    public int getCalificacion() {
        return calificacionL.getCalificacionPasajero(pasajero);
    }
    
    public boolean verificarPasajero(Chofer c) {
        return ayudante.verificarPasajero(c,pasajero);
    }
    
    public String calificar(Chofer ch) {
        calificacionL.calificarPasajero(calificacionPasajero,ch,pasajero);
        return "PerfilPasajeroIH";
    }
}
