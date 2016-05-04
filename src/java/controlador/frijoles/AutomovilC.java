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
    
    public String registro() {
        mensaje = ayudante.registrar(automovil,confirmacion);
        if(mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
         doStuff(automovil);
        return "RegistroRutaIH";
    }
    public String getConfirmacion() {
        return confirmacion;
    }
    
    /**
     * Temporal en memoria para que el registro de ruta sea exitoso
     * @param a 
     */
    private void doStuff(Automovil a) {
        Chofer tmp = (Chofer)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        Set<Automovil> s = new HashSet<>();
        s.add(a);
        tmp.setAutomovils(s);
        HttpServletRequest http = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        http.getSession().setAttribute("usuario",tmp);
        System.out.println(((Automovil)tmp.getAutomovils().iterator().next()).getModelo());
    }
}
