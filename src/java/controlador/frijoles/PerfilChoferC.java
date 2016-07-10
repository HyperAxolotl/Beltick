package controlador.frijoles;

import controlador.logica.CalificacionL;
import controlador.logica.HorarioL;
import controlador.logica.PerfilChoferL;
import controlador.logica.PerfilL;
import controlador.logica.RutaL;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import modelo.Imagen;
import modelo.Horario;
import modelo.Pasajero;
import modelo.PerfilChofer;
import modelo.Ruta;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.map.MapModel;

@Named(value = "perfilChoferC")
@ManagedBean
@ViewScoped
public class PerfilChoferC implements Serializable {

    private Chofer chofer;
    private PerfilChoferL ayudante;
    private CalificacionL calificacionL;
    private PerfilChofer perfil;
    private PerfilL perfilL;
    private Automovil auto;
    private RutaL rutaL = new RutaL();
    private CalificacionChofer calificacionChofer;
    private Imagen imagen;
    private Horario horario;
    private List<CalificacionChofer> calificaciones;
    private HorarioL horarioL;

    
    public PerfilChoferC() {
        chofer = new Chofer();
        ayudante = new PerfilChoferL();
        perfilL = new PerfilL();
        calificacionL = new CalificacionL();
        calificacionChofer = new CalificacionChofer();
        horarioL = new HorarioL();
        imagen = new Imagen();
    }

    @PostConstruct
    public void init() {
        System.out.print("Chofer...");
        int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("choferId"));
        chofer = ayudante.getChofer(id);
        System.out.print("Perfil...");
        perfil = ayudante.getPerfilChofer(chofer.getIdChofer());
        System.out.print("Auto...");
        if (tieneRuta()) {
            auto = ayudante.getAutomovil(id);
            System.out.println("Horario...");
            horario = extraeHorario();
        }
        imagen = ayudante.getImagenChofer(chofer.getIdChofer());
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
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
        if (r != null) {
            return rutaL.getModeloMapa(r.getMapa());
        }
        System.out.println("ruta nula");
        return null;
    }

    public Ruta getRuta() {
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

    public String fechaReg() {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        return dt1.format(perfil.getFechaCreacion());
    }

    public boolean sobreMi() {
        return perfil.getCsobreMi().equals("");
    }

    public int getCalificacion() {
        if (chofer != null) {
            return calificacionL.getCalificacionChofer(chofer);
        } 
        return 0;
    }

    public boolean verificarPasajero(Pasajero p) {
        return perfilL.verificarPasajero(chofer,p);
    }

    public String calificar(Pasajero p) {
        calificacionL.calificarChofer(calificacionChofer, p, chofer);
        System.out.println("chofer calificado");
        return "PerfilChoferIH";
    }

    public boolean verificarImagen() {
        return ayudante.getImagenChofer(chofer.getIdChofer()) != null;
    }
    
    public Horario extraeHorario() {
        return horarioL.getHorario(getRuta().getIdRuta());
    }
    
    public String formateaHora(Date hora) {
        return horarioL.formateaHora(hora);
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
    
    public String getEdad() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int anio = cal.get(Calendar.YEAR);
        cal.setTime(chofer.getCfechaNac());
        int aniop = cal.get(Calendar.YEAR);
        return anio - aniop + "";
    }
}


