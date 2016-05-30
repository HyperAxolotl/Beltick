package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

import modelo.Pasajero;
import controlador.logica.PasajeroL;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import modelo.PerfilPasajero;
import utiles.Cartero;

@ManagedBean
@ViewScoped
public class PasajeroC implements Serializable {

    private Pasajero pasajero = new Pasajero();
    private String confirmacion;
    private PasajeroL ayudante = new PasajeroL();
    private FacesMessage mensaje;
    private boolean exito;

    public PasajeroC() {
        ayudante = new PasajeroL();
    }

    public boolean isExito() {
        return exito;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public String registro() {
        mensaje = ayudante.registrar(pasajero, confirmacion);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        } else {
            Cartero cartero = new Cartero(ayudante.getPerfilPasajero(pasajero.getIdPasajero()));
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tu registro fue exitoso... Revisa en tu bandeja el correo de activación de tu cuenta.", null);
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            exito = true;
            System.out.println("Este es el valor de registro: " + exito);
            cartero.entrega();
            return "";
        }
    }

    public void setConfirmacion(String contrasenia) {
        confirmacion = contrasenia;
    }

    public String getConfirmacion() {
        return confirmacion;
    }
}
