package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

import modelo.Automovil;
import controlador.logica.AutomovilL;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import modelo.Chofer;

@ManagedBean
@SessionScoped
public class AutomovilC implements Serializable {

    private Automovil automovil = new Automovil();
    private String confirmacion;
    private AutomovilL ayudante = new AutomovilL();
    private FacesMessage mensaje;

    public AutomovilC() {
    }

    public Automovil getAutomovil() {
        return automovil;
    }

    public void setAutomovil(Automovil automovil) {
        this.automovil = automovil;
    }

    public String registro(Chofer chofer) {
        mensaje = ayudante.registrar(automovil, chofer);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        return "RegistroRutaIH";
    }

    public String getConfirmacion() {
        return confirmacion;
    }

}
