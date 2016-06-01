package controlador.frijoles;

import controlador.logica.BoletinL;
import controlador.logica.HorarioL;
import controlador.logica.PasajeroL;
import controlador.logica.RutaL;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.Boletin;
import modelo.Chofer;
import modelo.Horario;
import modelo.Pasajero;
import modelo.PasajeroRuta;
import modelo.Ruta;
import org.primefaces.model.map.MapModel;

@Named(value = "rutaC")
@ManagedBean
@ViewScoped
public class RutaC implements Serializable {

    private Ruta ruta;
    private Boletin boletin;
    private RutaL rutaL;
    private BoletinL boletinL;
    private List<Boletin> lstBoletin;
    private List<PasajeroRuta> lstPasajeros;
    private Horario horario;
    private HorarioL horarioL;
    private PasajeroL pasajeroL;
    private FacesMessage mensaje;

    public RutaC() {
        ruta = new Ruta();
        rutaL = new RutaL();
        boletin = new Boletin();
        boletinL = new BoletinL();
        horarioL = new HorarioL();
        pasajeroL = new PasajeroL();
    }

    @PostConstruct
    public void init() {
        ruta = rutaL.getRuta(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("rutaId")));
        rutaL.actualiza(ruta);
        horario = horarioL.getHorario(ruta.getIdRuta());
        listar();
        listarPasajeros();
    }

    public Boletin getBoletin() {
        return boletin;
    }
    
    public Horario getHorario() {
        return horario;
    }

    public void setBoletin(Boletin boletin) {
        this.boletin = boletin;
    }
    
    public List<Boletin> getLstBoletin() {
        return lstBoletin;
    }

    public void setLstBoletin(List<Boletin> lstBoletin) {
        this.lstBoletin = lstBoletin;
    }

    public List<PasajeroRuta> getLstPasajeros() {
        return lstPasajeros;
    }

    public void setLstPasajeros(List<PasajeroRuta> lstPasajeros) {
        this.lstPasajeros = lstPasajeros;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public MapModel getModeloMapa() {
        return rutaL.getModeloMapa(ruta.getMapa());
    }

    public boolean verificarMiembro(boolean b, Chofer c, Pasajero p) {
        if(b) {
            Chofer ch = rutaL.getChofer(ruta.getIdRuta());
            if(ch == null)
                return false;
            return ch.getIdChofer() == c.getIdChofer();
        }
        else {
            PasajeroL pL = new PasajeroL();
            List<PasajeroRuta> l = pL.listaRutas(p);
            for(PasajeroRuta pr : l) {
                if(pr.getId().getIdRuta() == ruta.getIdRuta())
                    return true;
            }
        }
        return false;
    }
    
    public void listar() {
        lstBoletin = boletinL.listar(ruta.getIdRuta());
    }
    
    public void listarPasajeros() {
        lstPasajeros = rutaL.listarPasajeros(ruta);
    }
    
    public void publicar() {
        boletinL.publicar(boletin,ruta);
        listar();
    }
    
    public String formateaHora(Date hora) {
        return horarioL.formateaHora(hora);
    }
    
    public String getPasajeroNombre(PasajeroRuta pr) {
        return rutaL.getPasajeroNombre(pr);
    }
    
    public String getPasajeroHora(PasajeroRuta pr) {
        return pasajeroL.getHora(pr);
    }
    
    public void eliminarPasajero(PasajeroRuta pr) {
        pasajeroL.eliminaRuta(pr);
        listarPasajeros();
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Pasajero eliminado de ruta");
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }
    
    public void eliminaPublicacion(Boletin b) {
        mensaje = boletinL.eliminaPublicacion(b);
        if (mensaje == null)
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Publicación borrada");
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
    }
}
