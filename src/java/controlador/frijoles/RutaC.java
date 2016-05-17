package controlador.frijoles;

import controlador.logica.BoletinL;
import controlador.logica.RutaL;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.Boletin;
import modelo.Chofer;
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

    public RutaC() {
        ruta = new Ruta();
        rutaL = new RutaL();
        boletin = new Boletin();
        boletinL = new BoletinL();
    }

    @PostConstruct
    public void init() {
        ruta = rutaL.getRuta(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("rutaId")));
        rutaL.actualiza(ruta);
        listar();
    }

    public Boletin getBoletin() {
        return boletin;
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
        if(b)
            return ruta.getAutomovil().getChofer().getIdChofer() == c.getIdChofer();
        else {
            Set s = p.getPasajeroRutas();
            for(Object o : s) {
                PasajeroRuta pr = (PasajeroRuta) o;
                if(pr.getRuta().getIdRuta() == ruta.getIdRuta())
                    return true;
            }
        }
        return false;
    }
    
    public void listar() {
        lstBoletin = boletinL.listar(ruta.getIdRuta());
    }
    
    public void publicar() {
        boletinL.publicar(boletin,ruta);
        listar();
    }
}
