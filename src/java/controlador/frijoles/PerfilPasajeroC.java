package controlador.frijoles;

import controlador.logica.PerfilL;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Pasajero;

@Named(value = "perfilPasajeroC")
@ManagedBean
@ViewScoped
public class PerfilPasajeroC {

    private Pasajero pasajero;
    private PerfilL ayudante;
    
    public PerfilPasajeroC() {
        pasajero = new Pasajero();
        ayudante = new PerfilL();
    }
    
    @PostConstruct
    public void init() {
        pasajero = ayudante.getPasajero(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pasajeroId")));
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    
}
