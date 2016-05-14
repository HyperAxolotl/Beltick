package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;

import modelo.Ruta;
import controlador.logica.RutaL;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Horario;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

@ManagedBean
@ViewScoped
public class RegistroRutaC implements Serializable {

    private Ruta ruta = new Ruta();
    private RutaL ayudante = new RutaL();
    private Horario horario = new Horario();
    private FacesMessage mensaje;
    private MapModel modeloMapa;
    private boolean exito;

    public RegistroRutaC() {
        ruta = new Ruta();
        ayudante = new RutaL();
        horario = new Horario();
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

    public String registro() {
        mensaje = ayudante.registrar(ruta, horario);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "error";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tu ruta se registró exitosamente", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        exito = true;
        return "";
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
