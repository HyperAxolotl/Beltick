package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

import modelo.Ruta;
import controlador.logica.RutaL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;


@ManagedBean
@ViewScoped
public class RutaC implements Serializable {
    
    private Ruta ruta = new Ruta();
    private RutaL ayudante = new RutaL();
    private List<Ruta> lstRutas;
    private MapModel modeloMapa;
    private FacesMessage mensaje;
    
    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }
    
    public String registro() {
        mensaje = ayudante.registrar(ruta);
        if(mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "error";
        }
        return "exito";
    }
       
    public List<Ruta> getLstRutas() {       
        return lstRutas;
    }
    
    public void listar() throws Exception {
        lstRutas = ayudante.listar();
    }
    
    public MapModel getModeloMapa(String polyencod) {  
        modeloMapa  = new DefaultMapModel();
        Polyline poly = new Polyline();
        List<LatLng> l = decodePolyline(polyencod);
        for(LatLng coord : l)
            poly.getPaths().add(coord);
        poly.setStrokeWeight(4);
        poly.setStrokeColor("#DC143C");
        poly.setStrokeOpacity(0.7);      
        modeloMapa.addOverlay(poly);
        return modeloMapa;
    }
    
    private List<LatLng> decodePolyline(String encoded) {
 
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
 
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
 
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;
 
            LatLng p = new LatLng((((double) lat / 1E5)),
                        (((double) lng / 1E5)));
            poly.add(p);
        }
 
        return poly;
    }
}
