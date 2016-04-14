package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

import modelo.Pasajero;
import controlador.logica.PasajeroL;
import javax.servlet.http.HttpServletRequest;


@ManagedBean
@SessionScoped
public class PasajeroC implements Serializable {

    private Pasajero pasajero = new Pasajero();
    private String confirmacion;
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
        mensaje = ayudante.registrar(pasajero,confirmacion);
        ayudante = new PasajeroL();
        if(mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        else {
            if(ayudante.generaPerfil(pasajero))
               return "exito";
            return "error";
        }
    }
    
    public void setConfirmacion(String contrasenia){
        confirmacion = contrasenia;
    }

    public String getConfirmacion() {
        return confirmacion;
    }
}
