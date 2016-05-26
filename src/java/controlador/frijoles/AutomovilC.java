package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import javax.faces.application.FacesMessage;

import modelo.Automovil;
import controlador.logica.AutomovilL;
import controlador.logica.RutaL;
import javax.faces.bean.ViewScoped;
import modelo.Chofer;
import modelo.Horario;
import modelo.Ruta;
import org.primefaces.model.map.MapModel;

@ManagedBean
@ViewScoped
public class AutomovilC implements Serializable {

    private Automovil automovil;
    private String confirmacion;
    private AutomovilL automovilL;
    private Ruta ruta;
    private RutaL rutaL;
    private Horario horario;
    private FacesMessage mensaje;
    private MapModel modeloMapa;
    private boolean exito;

    public AutomovilC() {
        automovil = new Automovil();
        automovilL = new AutomovilL();
        ruta = new Ruta();
        rutaL = new RutaL();
        horario = new Horario();
    }

    public Automovil getAutomovil() {
        return automovil;
    }

    public void setAutomovil(Automovil automovil) {
        this.automovil = automovil;
    }

    public String registro(Chofer chofer) {
        mensaje = automovilL.registrar(automovil, ruta, horario, chofer);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Datos registrados exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        exito = true;
        return "";
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public boolean isExito() {
        return exito;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public MapModel getModeloMapa() {
        return modeloMapa;
    }

}
