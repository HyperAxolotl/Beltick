package controlador.frijoles;

import controlador.logica.PasajeroL;
import controlador.logica.PerfilPasajeroL;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Chofer;
import modelo.Imagen;
import modelo.Pasajero;
import modelo.PerfilPasajero;
import org.primefaces.model.UploadedFile;

@Named(value = "actualizarPerfilPasajeroC")
@ManagedBean
@ViewScoped
public class ActualizarPerfilPasajeroC implements Serializable {

    private Pasajero pasajero;
    private PerfilPasajero perfil;
    private PasajeroL pasajeroL;
    private PerfilPasajeroL perfilL;
    private String fecha;
    private FacesMessage mensaje;
    private UploadedFile archivo;

    public ActualizarPerfilPasajeroC() {
        pasajeroL = new PasajeroL();
        perfilL = new PerfilPasajeroL();
        pasajero = (Pasajero) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("usuario");
        perfil = perfilL.getPerfilPasajero(pasajero.getIdPasajero());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        fecha = sdf.format(pasajero.getPfechaNac());
    }

    public UploadedFile getArchivo() {
        return archivo;
    }

    public void setArchivo(UploadedFile archivo) {
        this.archivo = archivo;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public void setPasajero(Pasajero pasajero) {
        this.pasajero = pasajero;
    }

    public PerfilPasajero getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilPasajero perfil) {
        this.perfil = perfil;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String actualizarPasajero() {
        Imagen imagen = null;
        if (archivo.getSize() > 0) {
            imagen = new Imagen();
            imagen.setImagen(archivo.getContents());
            imagen.setNombre(archivo.getFileName());
        }
        mensaje = pasajeroL.actualizarPasajero(pasajero);
        mensaje = perfilL.actualizarPerfilPasajero(perfil, imagen);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        fecha = sdf.format(pasajero.getPfechaNac());
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Perfil actualizado con éxito", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        return "";
    }
}
