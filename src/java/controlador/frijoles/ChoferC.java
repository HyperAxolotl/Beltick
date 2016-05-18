package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

import modelo.Chofer;
import controlador.logica.ChoferL;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import utiles.Cartero;


@ManagedBean
@ViewScoped
public class ChoferC implements Serializable {

    private Chofer chofer = new Chofer();
    private String confirmacion;
    private ChoferL ayudante = new ChoferL();
    private FacesMessage mensaje;
    private boolean exito;
    
    
    public ChoferC() {
    }
    
    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public boolean isExito() {
        return exito;
    }
    
    public String registro() {
        mensaje = ayudante.registrar(chofer,confirmacion);
        ayudante = new ChoferL();
        Cartero cartero = new Cartero(ayudante.getPerfilChofer(chofer.getIdChofer()));
        if(mensaje != null) {
            FacesContext.getCurrentInstance().addMessage("msg", mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tu registro fue exitoso... Revisa en tu bandeja el correo de activación de tu cuenta.", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        exito = true;
        System.out.println("Este es el valor de registro: "+exito);
        cartero.entrega();
        return "";
    }
    
    public void setConfirmacion(String contrasenia){
        confirmacion = contrasenia;
    }

    public String getConfirmacion() {
        return confirmacion;
    }
}
