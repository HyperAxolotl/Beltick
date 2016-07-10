package controlador.frijoles;

import controlador.logica.CalificacionL;
import controlador.logica.HorarioL;
import controlador.logica.PerfilL;
import controlador.logica.PerfilPasajeroL;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.CalificacionChofer;
import modelo.CalificacionPasajero;
import modelo.Chofer;
import modelo.Horario;
import modelo.Imagen;
import modelo.Pasajero;
import modelo.PerfilPasajero;
import modelo.Ruta;

@Named(value = "perfilPasajeroC")
@ManagedBean
@ViewScoped
public class PerfilPasajeroC implements Serializable {

    private Pasajero pasajero;
    private CalificacionL calificacionL;
    private PerfilPasajeroL ayudante;
    private CalificacionPasajero calificacionPasajero;
    private Imagen imagen;
    private PerfilL perfilL;
    private PerfilPasajero perfil;
    private List<CalificacionPasajero> calificaciones;
    private HorarioL horloge;
    private Horario horario;

    public PerfilPasajeroC() {
        pasajero = new Pasajero();
        ayudante = new PerfilPasajeroL();
        perfilL = new PerfilL();
        calificacionL = new CalificacionL();
        calificacionPasajero = new CalificacionPasajero();
        imagen = new Imagen();
        horloge = new HorarioL();

    }

    @PostConstruct
    public void init() {
        int id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pasajeroId"));
        pasajero = ayudante.getPasajero(id);
        imagen = ayudante.getImagenPasajero(id);
        perfil = ayudante.getPerfilPasajero(id);
        //horario = setHorario();
    }

    public boolean tieneRuta() {
        return ayudante.tieneRuta(pasajero);
    }

    public boolean enSesion(Pasajero p) {
        return p.getIdPasajero() == pasajero.getIdPasajero();
    }

    public Horario getHorario() {
        return horario;
    }

    public PerfilPasajero getPerfil() {
        return perfil;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    public CalificacionPasajero getCalificacionPasajero() {
        return calificacionPasajero;
    }

    public void setCalificacionPasajero(CalificacionPasajero calificacionPasajero) {
        this.calificacionPasajero = calificacionPasajero;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public int getCalificacion() {
        if (pasajero != null) {
            return calificacionL.getCalificacionPasajero(pasajero);
        }
        return 0;
    }

    public boolean verificarPasajero(Chofer c) {
        return perfilL.verificarPasajero(c, pasajero);
    }

    public String calificar(Chofer ch) {
        calificacionL.calificarPasajero(calificacionPasajero, ch, pasajero);
        return "PerfilPasajeroIH";
    }

    public boolean verificarImagen() {
        return ayudante.getImagenPasajero(pasajero.getIdPasajero()) != null;
    }

    public String fechaReg() {
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        return dt1.format(perfil.getFechaCreacion());
    }

    public String aHora(Date d) {
        return horloge.formateaHora(d);
    }

    public String horaDia(int dia) {
        Date lunes = ayudante.horaDia(pasajero.getIdPasajero(), dia);
        return horloge.formateaHora(lunes);
    }

    public int rutaDia1(int dia) {
        return 0;
    }

    public Ruta rutaDia(int dia) {
        return ayudante.rutaDia(pasajero.getIdPasajero(), dia);
    }

    public int elChofer(int dia) {
        Ruta r = rutaDia(dia);
        if (r != null) {
            Automovil a = r.getAutomovil();
            Chofer c = a.getChofer();
            return c.getIdChofer();
        }
        return -1;
    }

    public String getEdad() {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int anio = cal.get(Calendar.YEAR);
        cal.setTime(pasajero.getPfechaNac());
        int aniop = cal.get(Calendar.YEAR);
        return anio - aniop + "";
    }
    
    /*
    public String horaMartes() {
        System.out.println("Obteniendo hora en lunes");
        Date martes = ayudante.horaMartes(pasajero.getIdPasajero());
        return horloge.formateaHora(martes);
    }

    public String horaMiercoles() {
        System.out.println("Obteniendo hora en lunes");
        Date miercoles = ayudante.horaMiercoles(pasajero.getIdPasajero());
        return horloge.formateaHora(miercoles);
    }

    public String horaJueves() {
        System.out.println("Obteniendo hora en lunes");
        Date jueves = ayudante.horaJueves(pasajero.getIdPasajero());
        return horloge.formateaHora(jueves);
    }

    public String horaViernes() {
        System.out.println("Obteniendo hora en lunes");
        Date viernes = ayudante.horaViernes(pasajero.getIdPasajero());
        return horloge.formateaHora(viernes);
    }

    public String hora() {
        System.out.println("Obteniendo hora en lunes");
        Date = ayudante.horaMartes(pasajero.getIdPasajero());
        return horloge.formateaHora();
    }*/
    
    public List<CalificacionPasajero> getCalificaciones() {
        calificaciones = ayudante.listarCalificaciones(pasajero.getIdPasajero());
        return calificaciones;
    }
    
    public String formateoDia(Date d){
        SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
        return dt1.format(d);
    }
    
    public void listarCalificaciones() {
        calificaciones = ayudante.listarCalificaciones(pasajero.getIdPasajero());
        System.out.println("Estamos aqui...");
    }
}
