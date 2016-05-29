package controlador.frijoles;

import controlador.logica.CalificacionL;
import controlador.logica.PerfilChoferL;
import controlador.logica.PerfilL;
import controlador.logica.RutaL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.CalificacionChofer;
import modelo.Chofer;
import modelo.Horario;
import modelo.Pasajero;
import modelo.PerfilChofer;
import modelo.Ruta;
import org.primefaces.model.map.MapModel;

@Named(value = "perfilChoferC")
@ManagedBean
@ViewScoped
public class PerfilChoferC {

    private Chofer chofer;
    private PerfilChoferL ayudante;
    private CalificacionL calificacionL;
    private PerfilChofer perfil;
    private PerfilL perfilL;
    private Automovil auto;
    private RutaL rutaL = new RutaL();
    private CalificacionChofer calificacionChofer;
    private Horario horario;
    private List<CalificacionChofer> calificaciones;
    
    public PerfilChoferC() {
        chofer = new Chofer();
        ayudante = new PerfilChoferL();
        perfilL = new PerfilL();
        calificacionL = new CalificacionL();
        calificacionChofer = new CalificacionChofer();
    }
    
    //@PostConstruct
    public void init() {
        System.out.println("Chofer..."); 
        int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("choferId"));
        chofer = ayudante.getChofer(id);
        System.out.print("Perfil...");
        perfil = (PerfilChofer)chofer.getPerfilChofers().iterator().next();
        System.out.print("Auto...");
        if(tieneRuta()) {
            auto = ayudante.getAutomovil(id);
            System.out.println("Horario...");
            horario = extraeHorario();
        }
    }

    public Horario getHorario() {
        return horario;
    }

    public List<CalificacionChofer> getCalificaciones() {
        calificaciones = ayudante.listarCalificaciones(chofer.getIdChofer());
        return calificaciones;
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

    public CalificacionChofer getCalificacionChofer() {
        return calificacionChofer;
    }

    public void setCalificacionChofer(CalificacionChofer calificacionChofer) {
        this.calificacionChofer = calificacionChofer;
    }
    
    public String fechaReg(){
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        return dt1.format(perfil.getFechaCreacion());
    }
    
    public boolean sobreMi(){
        return perfil.getCsobreMi().equals("");
    }
    
    public int getCalificacion() {
        return calificacionL.getCalificacionChofer(chofer);
    }
    
    public boolean verificarPasajero(Pasajero p) {
        return perfilL.verificarPasajero(chofer,p);
    }
    
    public String calificar(Pasajero p) {
        calificacionL.calificarChofer(calificacionChofer,p,chofer);
        return "PerfilChoferIH";
    }
    
    public Horario extraeHorario() {
        return (Horario)getRuta().getHorarios().iterator().next();
    }
    
    public String formateoDia(Date d){
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        return dt1.format(d);
    }
    
    /**Deprecated xd*/
    public void listarCalificaciones() {
        calificaciones = ayudante.listarCalificaciones(chofer.getIdChofer());
        System.out.println("Estamos aqui...");
    }
}