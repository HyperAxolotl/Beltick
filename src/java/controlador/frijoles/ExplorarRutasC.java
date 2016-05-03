package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import java.io.Serializable;

import modelo.Ruta;
import controlador.logica.RutaL;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Pasajero;
import modelo.Solicitud;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

@ManagedBean
@ViewScoped
public class ExplorarRutasC implements Serializable {

    private Ruta ruta;
    private RutaL ayudante;
    private List<Ruta> lstRutas;
    private MapModel modeloMapa;
    private FacesMessage mensaje;
    private double lat;
    private double lng;
    private double radio;
    
    public ExplorarRutasC() {
        ruta = new Ruta();
        ayudante = new RutaL();
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getRadio() {
        return radio;
    }

    public void setRadio(double radio) {
        this.radio = radio;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public List<Ruta> getLstRutas() {
        return lstRutas;
    }

    public void listar() throws Exception {
        lstRutas = ayudante.listar();
    }

    public void listarRadio() throws Exception {
        lstRutas = ayudante.listar();
        List<Ruta> l = new ArrayList<>();
        boolean b = true;
        for (Ruta ruta : lstRutas) {
            for (LatLng latlng : decodePolyline(ruta.getMapa())) {
                if (haversine(latlng.getLat(), latlng.getLng()) <= radio && b) {
                    l.add(ruta);
                    b = false;
                }
            }
            b = true;
        }
        lstRutas = l;
    }

    public MapModel getModeloMapa() {
        return modeloMapa;
    }

    public MapModel getModeloMapa(String polyencod) {
        modeloMapa = new DefaultMapModel();
        Polyline poly = new Polyline();
        List<LatLng> l = decodePolyline(polyencod);
        for (LatLng coord : l) {
            poly.getPaths().add(coord);
        }
        poly.setStrokeWeight(4);
        poly.setStrokeColor("#DC143C");
        poly.setStrokeOpacity(0.7);
        modeloMapa.addOverlay(poly);
        return modeloMapa;
    }

    //Regresa distancia entre dos puntos en km
    private double haversine(double lat2, double lng2) {
        int R = 6371;
        double latD = Math.toRadians(lat2 - lat);
        double lngD = Math.toRadians(lng2 - lng);
        double a = Math.sin(latD / 2) * Math.sin(latD / 2)
                + Math.cos(Math.toRadians(lat))
                * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngD / 2) * Math.sin(lngD / 2);
        return R * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
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
