package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

import modelo.Pasajero;
import controlador.logica.PasajeroL;

@ManagedBean
@SessionScoped
public class PasajeroC implements Serializable {

    private Pasajero pasajero = new Pasajero();
    private PasajeroL ayudante = new PasajeroL();
    private FacesMessage mensaje;
    
    public PasajeroC() {
    }
    
    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }
    
    public String registro() { 
        boolean exito;
        exito = ayudante.registrar(pasajero);
        ayudante = new PasajeroL();
        if(exito) 
            return "exito";
        else {
            //mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "La cagaste", null);
            //faceContext.addMessage(null, mensaje);
            return "error";
        }
    }
}
