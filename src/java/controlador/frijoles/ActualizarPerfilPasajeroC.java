package controlador.frijoles;

import controlador.logica.PasajeroL;
import controlador.logica.PerfilPasajeroL;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Chofer;
import modelo.Imagen;
import modelo.Pasajero;
import modelo.PasajeroRuta;
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
    private List<PasajeroRuta> listaRutas;

    public ActualizarPerfilPasajeroC() {
        pasajeroL = new PasajeroL();
        perfilL = new PerfilPasajeroL();
        pasajero = (Pasajero) FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().get("usuario");
        perfil = perfilL.getPerfilPasajero(pasajero.getIdPasajero());
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        fecha = sdf.format(pasajero.getPfechaNac());
        listaRutas();
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

    public List<PasajeroRuta> getListaRutas() {
        return listaRutas;
    }

    public void setListaRutas(List<PasajeroRuta> listaRutas) {
        this.listaRutas = listaRutas;
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
    
    public void listaRutas() {
        listaRutas = pasajeroL.listaRutas(pasajero);
    }
    
    public String getHora(PasajeroRuta pr) {
        return pasajeroL.getHora(pr);
    }
    
    public int getChoferId(PasajeroRuta pr) {
        return pasajeroL.getChoferId(pr);
    }
    
    public void eliminaRuta(PasajeroRuta pr) {
        mensaje = pasajeroL.eliminaRuta(pr);
        listaRutas();
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }
    
    public void eliminarCuenta() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        mensaje = pasajeroL.eliminarCuenta(pasajero);
        FacesContext.getCurrentInstance().getExternalContext()
                .invalidateSession();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("PaginaPrincipalIH.xhtml");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
