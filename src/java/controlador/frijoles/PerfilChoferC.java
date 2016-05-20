package controlador.frijoles;

import controlador.logica.PerfilL;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.Chofer;
import modelo.Ruta;

@Named(value = "perfilChoferC")
@ManagedBean
@ViewScoped
public class PerfilChoferC {

    private Chofer chofer;
    private PerfilL ayudante;
    
    public PerfilChoferC() {
        chofer = new Chofer();
        ayudante = new PerfilL();
    }
    
    @PostConstruct
    public void init() {
        chofer = ayudante.getChofer(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("choferId")));
    }
    
    public boolean tieneRuta() {
        return ayudante.tieneRuta(chofer);
    }
    
    public int getIdRuta() {
        return ayudante.getIdRuta(chofer);
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }
    
}