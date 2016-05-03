package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

import modelo.Automovil;
import controlador.logica.AutomovilL;
import javax.servlet.http.HttpServletRequest;


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
    
    public String registro() {
        mensaje = ayudante.registrar(automovil,confirmacion);
        if(mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        return "RegistroRutaIH";
    }
    public String getConfirmacion() {
        return confirmacion;
    }
}
