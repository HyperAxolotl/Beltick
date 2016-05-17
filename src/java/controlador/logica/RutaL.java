package controlador.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Horario;
import modelo.Pasajero;
import modelo.Ruta;
import modelo.Solicitud;
import modelo.SolicitudId;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Polyline;

public class RutaL implements Serializable {

    private Session con;
    private Transaction trans;
    private List<Ruta> lstRutas;

    public List<Ruta> listar() throws Exception {
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            Criteria cri = con.createCriteria(Ruta.class);
            lstRutas = cri.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstRutas;
    }

    public FacesMessage registrar(Ruta r, Horario h) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            Date fecha = new Date();
            String mapa = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("mapa");
            r.setMapa(mapa);
            r.setActiva(true);
            r.setFechaCreacion(fecha);
            con.save(r);
            h.setRuta(r);
            con.save(h);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error con el registro de la ruta", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }

    }

    public FacesMessage solicitar(Pasajero p, Ruta r, String[] dias) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            for(String s : dias) {
                Solicitud sol = new Solicitud();
                SolicitudId sid = new SolicitudId();
                sid.setDia(s);
                sid.setIdPasajero(p.getIdPasajero());
                sid.setIdRuta(r.getIdRuta());
                sol.setId(sid);
                sol.setPasajero(p);
                sol.setRuta(r);
                con.save(sol);
                p.getSolicituds().add(sol);
            }
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error con el registro de la solicitud", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }

    public Ruta getRuta(int id) {
        if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
        Query query = con.createQuery("from Ruta where id_ruta = :id ");
        query.setParameter("id", id);
        List<?> list = query.list();
        Ruta r = (Ruta) list.get(0);
        con.close();
        return r;
    }
    
    public MapModel getModeloMapa(String polyencod) {
        MapModel modeloMapa = new DefaultMapModel();
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
    
    public List<LatLng> decodePolyline(String encoded) {

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
    
    public void actualiza(Ruta ruta) {
         if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
         con.update(ruta);
    }
}
