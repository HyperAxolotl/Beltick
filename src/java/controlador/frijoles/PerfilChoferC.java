package controlador.frijoles;

import controlador.logica.PerfilL;
import controlador.logica.RutaL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.Chofer;
import modelo.PerfilChofer;
import modelo.Ruta;
import org.primefaces.model.map.MapModel;

@Named(value = "perfilChoferC")
@ManagedBean
@ViewScoped
public class PerfilChoferC {

    private Chofer chofer;
    private PerfilL ayudante;
    private PerfilChofer perfil;
    private Automovil auto;
    private RutaL rutaL = new RutaL();
    
    public PerfilChoferC() {
        chofer = new Chofer();
        ayudante = new PerfilL();
    }
    //@PostConstruct
    public void init() {
        System.out.println("Chofer..."); 
        int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("choferId"));
        chofer = ayudante.getChofer(id);
        System.out.println("Perfil...");
        perfil = (PerfilChofer)chofer.getPerfilChofers().iterator().next();
        System.out.println("Auto...");
        auto = ayudante.getAutomovil(id);
    }

    public Automovil getAuto() {
        return auto;
    }
    
    public MapModel getModeloMapa() {
        Ruta r = getRuta();
        if(r != null)
            return rutaL.getModeloMapa(r.getMapa());
        System.out.println("ruta nula");
        return null;
    }
    
    public Ruta getRuta(){
        return ayudante.getRuta(auto.getIdAutomovil());
    }

    public PerfilChofer getPerfil() {
        return perfil;
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
    
    public String fechaReg(){
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        return dt1.format(perfil.getFechaCreacion());
    }
    
    public boolean sobreMi(){
        return perfil.getCsobreMi().equals("");
    }
    
}