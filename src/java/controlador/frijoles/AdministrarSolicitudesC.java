package controlador.frijoles;

import controlador.logica.SolicitudL;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Chofer;
import modelo.Solicitud;

@ManagedBean
@ViewScoped
public class AdministrarSolicitudesC implements Serializable {

    private Solicitud solicitud;
    private SolicitudL ayudante;
    private FacesMessage mensaje;
    private List<Solicitud> lstSolicitudes;
    
    public AdministrarSolicitudesC() {
        solicitud = new Solicitud();
        ayudante = new SolicitudL();
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

    public void eliminarSolicitud(Solicitud s, int id) throws Exception {
        mensaje = ayudante.eliminar(s);
        if (mensaje == null) 
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "La solicitud ha sido eliminada", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        listarSolicitudes(id);           
    }

    public void registrarSolicitud(Solicitud s, int id) throws Exception {
        mensaje = ayudante.registrar(s);
        if (mensaje == null)
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "La solicitud ha sido aceptada", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        listarSolicitudes(id); 
    }

    public void listarSolicitudes(int idChofer) throws Exception {
        lstSolicitudes = ayudante.listar();
        List<Solicitud> l = new ArrayList<>();
        for (Solicitud s : lstSolicitudes)
            if (s.getRuta().getAutomovil().getChofer().getIdChofer() == idChofer)
                l.add(s);
        lstSolicitudes = l;
    }
    
    public String getHora(Solicitud solicitud) throws Exception{
        return ayudante.getHora(solicitud);
    }
    
}
