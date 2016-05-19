package controlador.logica;

import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Chofer;
import modelo.ConexionBD;
import modelo.Horario;
import modelo.MensajeChofer;
import modelo.MensajePasajero;
import modelo.NotificacionChofer;
import modelo.NotificacionPasajero;
import modelo.Pasajero;
import modelo.Ruta;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MensajeL {
    
    private Session con;
    private Transaction trans;
    private List<MensajeChofer> lstMensajesC;
    private List<MensajePasajero> lstMensajesP;
    
    public List<MensajeChofer> listarChofer(Chofer chofer) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from MensajeChofer m where m.chofer.idChofer = :id order by m.fecha desc");
            query.setParameter("id", chofer.getIdChofer());
            lstMensajesC = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstMensajesC;
    }

    public List<MensajePasajero> listarPasajero(Pasajero pasajero) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from MensajePasajero m where m.pasajero.idPasajero = :id order by m.fecha desc");
            query.setParameter("id", pasajero.getIdPasajero());
            lstMensajesP = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstMensajesP;
    }
    
    public Chofer getChofer(int id) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Query query = con.createQuery("from Chofer where idChofer = :id ");
        query.setParameter("id", id);
        List<?> list = query.list();
        Chofer c = (Chofer) list.get(0);
        con.close();
        return c;
    }
    
    public Pasajero getPasajero(int id) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Query query = con.createQuery("from Pasajero where idPasajero = :id ");
        query.setParameter("id", id);
        List<?> list = query.list();
        Pasajero p = (Pasajero) list.get(0);
        con.close();
        return p;
    }
    
    public FacesMessage registroMensajeC(MensajeChofer mc, Pasajero p, Chofer c) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            Date fecha = new Date();
            mc.setFecha(fecha);
            mc.setPasajero(p);
            mc.setChofer(c);
            con.save(mc);
            NotificacionChofer nc = new NotificacionChofer();
            nc.setTitulo("Mensaje recibido");
            nc.setContenido(String.format("Has recibido un mensaje del pasajero %s",
                    p.getPnombre()));
            nc.setFecha(fecha);
            nc.setChofer(c);
            nc.setVisto(false);
            con.save(nc);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al enviar mensaje", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }

    }
    
    public FacesMessage registroMensajeP(MensajePasajero mp, Chofer c, Pasajero p) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            Date fecha = new Date();
            mp.setFecha(fecha);
            mp.setPasajero(p);
            mp.setChofer(c);
            con.save(mp);
            NotificacionPasajero np = new NotificacionPasajero();
            np.setTitulo("Mensaje recibido");
            np.setContenido(String.format("Has recibido un mensaje del chofer %s",
                    c.getCnombre()));
            np.setFecha(fecha);
            np.setPasajero(p);
            np.setVisto(false);
            con.save(np);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al enviar mensaje", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }

    }
}
