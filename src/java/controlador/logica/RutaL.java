package controlador.logica;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.Chofer;

import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Horario;
import modelo.NotificacionChofer;
import modelo.NotificacionPasajero;
import modelo.Pasajero;
import modelo.PasajeroRuta;
import modelo.Ruta;
import modelo.Solicitud;
import modelo.SolicitudId;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
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
    private List<PasajeroRuta> lstPasajeros;

    public List<Ruta> listar() {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Criteria cri = con.createCriteria(Ruta.class);
            lstRutas = cri.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstRutas;
    }

    public FacesMessage solicitar(Pasajero p, Ruta r, String[] dias) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            for (String s : dias) {
                Solicitud sol = new Solicitud();
                SolicitudId sid = new SolicitudId();
                sid.setDia(s);
                sid.setIdPasajero(p.getIdPasajero());
                sid.setIdRuta(r.getIdRuta());
                sol.setId(sid);
                sol.setPasajero(p);
                sol.setRuta(r);
                con.save(sol);
                NotificacionChofer nc = new NotificacionChofer();
                nc.setTitulo("Solicitud de servicio");
                nc.setContenido(String.format("Tienes una nueva solicitud de servicio "
                        + "de parte del pasajero %s para el día %s", p.getPnombre(), sid.getDia()));
                Date fecha = new Date();
                nc.setFecha(fecha);
                Automovil a = (Automovil)con.get(Automovil.class,r.getAutomovil().getIdAutomovil());
                Chofer ch = (Chofer)con.get(Chofer.class,a.getChofer().getIdChofer());
                nc.setChofer(ch);
                nc.setVisto(false);
                con.save(nc);
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
        Ruta r = null;
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            r = (Ruta)con.get(Ruta.class,id);
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            con.close();
            return r;
        }
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
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        con.update(ruta);
    }

    public FacesMessage actualizarRuta(Ruta r) {
        FacesMessage mensaje = null;
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            con.update(r);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al actualizar horario", null);
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
    }
    
    public Ruta getAutomovilRuta(int automovilId) {
        Ruta a = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            String hql = "FROM Ruta WHERE automovil.idAutomovil= :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", automovilId);
            if (!query.list().isEmpty()) {
                a = (Ruta) query.list().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }
    
    public FacesMessage actualizar(Ruta r) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            String mapa = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("mapa");
            r.setMapa(mapa);
            con.update(r);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al actualizar la ruta", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }

    }
    
    public Chofer getChofer(int idRuta) {
        Chofer a = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            String hql = "SELECT c FROM Ruta r join r.automovil a join a.chofer c WHERE r.idRuta = :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", idRuta);
            List<Chofer> l = query.list();
            if (!l.isEmpty()) {
                a = l.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }
    
    public List<PasajeroRuta> listarPasajeros(Ruta r) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM PasajeroRuta WHERE id.idRuta = :id "
                    + "ORDER BY id.dia";
            Query query = con.createQuery(hql);
            query.setParameter("id", r.getIdRuta());
            lstPasajeros = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstPasajeros;
    }
    
    public String getPasajeroNombre(PasajeroRuta s) {
        String b = "";
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select p.pnombre from Pasajero p "
                    + "where p.idPasajero = :id");
            query.setParameter("id", s.getId().getIdPasajero());
            List<String> list = query.list();
            if (list.size() > 0) {
                b = list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return b;
        }
    }
    
}
