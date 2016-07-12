package controlador.logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import modelo.Boletin;
import modelo.CalificacionChofer;
import modelo.CalificacionPasajero;
import modelo.Chofer;
import modelo.ConexionBD;
import modelo.NotificacionChofer;
import modelo.NotificacionPasajero;
import modelo.Pasajero;
import modelo.Ruta;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class CalificacionL implements Serializable {

    private Session con;
    private Transaction trans;

    public FacesMessage calificarChofer(CalificacionChofer c, Pasajero p, Chofer ch) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from CalificacionChofer c where c.pasajero.idPasajero = :idP and c.chofer.idChofer = :idC");
            query.setParameter("idP", p.getIdPasajero());
            query.setParameter("idC", ch.getIdChofer());
            List<CalificacionChofer> l = query.list();
            trans = con.beginTransaction();
            Date fecha = new Date();
            if (l.size() > 0) {
                CalificacionChofer cp = l.get(0);
                cp.setCalificacion(c.getCalificacion());
                cp.setDescripcion(c.getDescripcion());
                cp.setFecha(fecha);
                con.update(cp);
            } else {
                c.setFecha(fecha);
                c.setPasajero(p);
                c.setChofer(ch);
                con.save(c);
            }
            NotificacionChofer np = new NotificacionChofer();
            np.setTitulo("Calificación recibida");
            np.setContenido(String.format("Haz sido calificado por el pasajero %s", p.getPnombre()));
            np.setFecha(fecha);
            np.setChofer(ch);
            np.setVisto(false);
            con.save(np);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al calificar chofer", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }

    public int getCalificacionChofer(Chofer chofer) {
        int d = 0;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select avg(calificacion) from CalificacionChofer c where c.chofer.idChofer = :id");
            query.setParameter("id", chofer.getIdChofer());
            List<Double> l = query.list();
            if (!l.isEmpty() && l.get(0) != null) {
                d = (int) Math.round(l.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return d;
        }
    }

    public FacesMessage calificarPasajero(CalificacionPasajero c, Chofer ch, Pasajero p) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from CalificacionPasajero c where c.pasajero.idPasajero = :idP and c.chofer.idChofer = :idC");
            query.setParameter("idP", p.getIdPasajero());
            query.setParameter("idC", ch.getIdChofer());
            List<CalificacionPasajero> l = query.list();
            trans = con.beginTransaction();
            Date fecha = new Date();
            if (l.size() > 0) {
                CalificacionPasajero cp = l.get(0);
                cp.setCalificacion(c.getCalificacion());
                cp.setDescripcion(c.getDescripcion());
                cp.setFecha(fecha);
                con.update(cp);
            } else {
                c.setFecha(fecha);
                c.setPasajero(p);
                c.setChofer(ch);
                con.save(c);
            }
            NotificacionPasajero np = new NotificacionPasajero();
            np.setTitulo("Calificación recibida");
            np.setContenido(String.format("Haz sido calificado por el chofer %s", ch.getCnombre()));
            np.setFecha(fecha);
            np.setPasajero(p);
            np.setVisto(false);
            con.save(np);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al calificar pasajero", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }

    public int getCalificacionPasajero(Pasajero pasajero) {
        int d = 0;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select avg(calificacion) from CalificacionPasajero c where c.pasajero.idPasajero = :id");
            query.setParameter("id", pasajero.getIdPasajero());
            List<Double> l = query.list();
            if (!l.isEmpty() && l.get(0) != null) {
                d = (int) Math.round(l.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return d;
        }
    }

}
