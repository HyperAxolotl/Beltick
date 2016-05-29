package controlador.frijoles;

import controlador.logica.SolicitudL;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.Chofer;
import modelo.Horario;
import modelo.PasajeroRuta;
import modelo.Ruta;
import modelo.Solicitud;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Hibernate;

@ManagedBean
@ViewScoped
public class AdministrarSolicitudesC implements Serializable {

    private Solicitud solicitud;
    private SolicitudL ayudante;
    private FacesMessage mensaje;
    private List<Solicitud> lstSolicitudes;
    private Chofer chofer;

    public AdministrarSolicitudesC() throws Exception {
        solicitud = new Solicitud();
        ayudante = new SolicitudL();
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario").getClass().getSimpleName().equals("Chofer")) {
            chofer = (Chofer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            listarSolicitudes();
        }
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public List<Solicitud> getLstSolicitudes() {
        return lstSolicitudes;
    }

    public void setLstSolicitudes(List<Solicitud> lstSolicitudes) {
        this.lstSolicitudes = lstSolicitudes;
    }

    public void eliminarSolicitud(Solicitud s) throws Exception {
        mensaje = ayudante.eliminar(s,chofer);
        if (mensaje == null) {
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "La solicitud ha sido eliminada", null);
        }
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        listarSolicitudes();
    }

    public void registrarSolicitud(Solicitud s) throws Exception {
        mensaje = ayudante.registrar(s,chofer);
        if (mensaje == null) {
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "La solicitud ha sido aceptada", null);
        }
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        listarSolicitudes();
    }

    public void listarSolicitudes() throws Exception {
        lstSolicitudes = ayudante.listar(chofer.getIdChofer());
    }

    public String getHora(Solicitud solicitud) throws Exception {
        return ayudante.getHora(solicitud);
    }

    //Regresa true si aun hay espacio en el coche del chofer en el dia recibido como parametro
    public boolean verificarDisponibilidad(Solicitud s) {
        return ayudante.verificarDisponibilidad(s.getId().getIdRuta(), s.getId().getDia());
    }

}
