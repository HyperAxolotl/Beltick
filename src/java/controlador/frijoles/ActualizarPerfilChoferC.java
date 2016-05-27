package controlador.frijoles;

import controlador.logica.AutomovilL;
import controlador.logica.ChoferL;
import controlador.logica.HorarioL;
import controlador.logica.PerfilChoferL;
import controlador.logica.RutaL;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.Chofer;
import modelo.Horario;
import modelo.Imagen;
import modelo.PerfilChofer;
import modelo.Ruta;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.MapModel;

@Named(value = "actualizarPerfilChoferC")
@ManagedBean
@ViewScoped
public class ActualizarPerfilChoferC implements Serializable {

    private Chofer chofer;
    private Automovil automovil;
    private Horario horario;
    private Ruta ruta;
    private PerfilChofer perfil;
    private ChoferL choferL;
    private AutomovilL automovilL;
    private RutaL rutaL;
    private PerfilChoferL perfilL;
    private HorarioL horarioL;
    private FacesMessage mensaje;
    private String fecha;
    private MapModel modeloMapa;
    private UploadedFile archivo;

    public ActualizarPerfilChoferC() {
        chofer = (Chofer) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("usuario");
        choferL = new ChoferL();
        automovilL = new AutomovilL();
        rutaL = new RutaL();
        horarioL = new HorarioL();
        perfilL = new PerfilChoferL();
        automovil = automovilL.getAutomovil(chofer.getIdChofer());
        ruta = rutaL.getAutomovilRuta(automovil.getIdAutomovil());
        horario = horarioL.getHorario(ruta.getIdRuta());
        perfil = perfilL.getPerfilChofer(chofer.getIdChofer());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        fecha = sdf.format(chofer.getCfechaNac());
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public MapModel getModeloMapa() {
        return modeloMapa;
    }

    public void setModeloMapa(MapModel modeloMapa) {
        this.modeloMapa = modeloMapa;
    }

    public Chofer getChofer() {
        return chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public PerfilChofer getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilChofer perfil) {
        this.perfil = perfil;
    }

    public Automovil getAutomovil() {
        return automovil;
    }

    public void setAutomovil(Automovil automovil) {
        this.automovil = automovil;
    }

    public String actualizarChofer() {
        Imagen imagen = null;
        if (archivo.getSize() > 0) {
            imagen = new Imagen();
            imagen.setImagen(archivo.getContents());
            imagen.setNombre(archivo.getFileName());
        }
        mensaje = choferL.actualizarChofer(chofer);
        mensaje = perfilL.actualizarPerfilChofer(perfil, imagen);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        fecha = sdf.format(chofer.getCfechaNac());
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos del chofer actualizados con éxito", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        return "";
    }

    public String actualizarAutomovil() {
        mensaje = automovilL.actualizarAutomovil(automovil);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos del automóvil actualizados con éxito", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        return "";
    }

    public String actualizarRuta() {
        mensaje = rutaL.actualizar(ruta);
        modeloMapa = new DefaultMapModel();
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos de la ruta actualizados con éxito", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        return "";
    }

    public String actualizarHorario() {
        mensaje = horarioL.actualizarHorario(horario);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Horario actualizado con éxito", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        return "";
    }
}
