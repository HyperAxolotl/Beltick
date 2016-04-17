package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

import modelo.Chofer;
import controlador.logica.ChoferL;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.HttpServletRequest;


@ManagedBean
@RequestScoped
public class ChoferC implements Serializable {

    private Chofer chofer = new Chofer();
    private String confirmacion;
    private ChoferL ayudante = new ChoferL();
    private FacesMessage mensaje;
    
    
    public ChoferC() {
    }
    
    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }
    
    public String registro() {
        mensaje = ayudante.registrar(chofer,confirmacion);
        ayudante = new ChoferL();
        if(mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        return "exito";
    }
    
    public void setConfirmacion(String contrasenia){
        confirmacion = contrasenia;
    }

    public String getConfirmacion() {
        return confirmacion;
    }
}
