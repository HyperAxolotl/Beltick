package controlador.logica;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Chofer;

import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.NotificacionPasajero;
import modelo.Pasajero;
import modelo.PasajeroRuta;
import modelo.PasajeroRutaId;
import modelo.Solicitud;
import modelo.SolicitudId;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;

public class SolicitudL implements Serializable {

    private Session con;
    private Transaction trans;
    private List<Solicitud> lstSolicitudes;

    public List<Solicitud> listar(int idChofer) throws Exception {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "select s from Solicitud s join s.ruta r "
                    + "join r.automovil a join a.chofer c where c.idChofer = :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", idChofer);
            lstSolicitudes = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstSolicitudes;
    }

    public FacesMessage eliminar(Solicitud s, Chofer ch) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            NotificacionPasajero np = new NotificacionPasajero();
            np.setTitulo("Solicitud rechazada");
            np.setContenido(String.format("Tu solicitud de servicio al chofer %s "
                    + "para el dia %s ha sido rechazada", ch.getCnombre(), s.getId().getDia()));
            Date fecha = new Date();
            np.setFecha(fecha);
            np.setPasajero(s.getPasajero());
            np.setVisto(false);
            con.delete(s);
            con.save(np);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al eliminar solicitud", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }

    public FacesMessage registrar(Solicitud s, Chofer ch) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            PasajeroRuta pr = new PasajeroRuta();
            PasajeroRutaId prid = new PasajeroRutaId();
            prid.setDia(s.getId().getDia());
            prid.setIdPasajero(s.getPasajero().getIdPasajero());
            prid.setIdRuta(s.getRuta().getIdRuta());
            pr.setId(prid);
            pr.setPasajero(s.getPasajero());
            pr.setRuta(s.getRuta());
            con.save(pr);
            Query query = con.createQuery("delete from Solicitud where "
                    + "id.idPasajero = :idP and id.dia = :dia");
            query.setParameter("idP", s.getId().getIdPasajero());
            query.setParameter("dia", s.getId().getDia());
            int i = query.executeUpdate();
            System.out.println(i + " solicitudes eliminadas");
            NotificacionPasajero np = new NotificacionPasajero();
            np.setTitulo("Solicitud aceptada");
            np.setContenido(String.format("Tu solicitud de servicio al chofer %s "
                    + "para el dia %s ha sido aceptada", ch.getCnombre(), pr.getId().getDia()));
            Date fecha = new Date();
            np.setFecha(fecha);
            np.setPasajero(pr.getPasajero());
            np.setVisto(false);
            con.save(np);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al procesar solicitud", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }

    public String getHora(Solicitud s) {
        Date d = null;
        String st = "";
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select h." + StringUtils.stripAccents(s.getId().getDia()).toLowerCase()
                    + " from Solicitud s join s.ruta r join r.horarios h where r.idRuta = :idruta");
            query.setParameter("idruta", s.getId().getIdRuta());
            List<?> list = query.list();
            d = (Date) list.get(0);
            if (d != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                st = sdf.format(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return st;
        }
    }

    public boolean verificarDisponibilidad(int idr, String d) {
        boolean b = true;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select a.capacidad from PasajeroRuta pr join pr.ruta r join r.automovil a "
                    + "where r.idRuta = :idruta AND pr.id.dia = :dia");
            query.setParameter("dia", d);
            query.setParameter("idruta", idr);
            List<?> list = query.list();
            if (list.size() > 0) {
                b = list.size() < (Integer) list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            b = false;
        } finally {
            con.close();
            return b;
        }
    }

    public boolean noDisponible(Pasajero pasajero, String dia, int idRuta) {
        boolean b = false;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from PasajeroRuta pr "
                    + "where pr.id.idPasajero = :idP AND pr.id.dia = :dia");
            query.setParameter("dia", dia);
            query.setParameter("idP", pasajero.getIdPasajero());
            List<PasajeroRuta> list = query.list();
            if (!list.isEmpty()) {
                b = true;
            }
            query = con.createQuery("from Solicitud s "
                    + "where s.id.idPasajero = :idP and s.id.dia = :dia and s.id.idRuta = :idR");
            query.setParameter("dia", dia);
            query.setParameter("idP", pasajero.getIdPasajero());
            query.setParameter("idR", idRuta);
            List<?> list2 = query.list();
            if (!list2.isEmpty()) {
                b = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            b = true;
        } finally {
            con.close();
            return b;
        }
    }
    
    public String getPasajeroNombre(Solicitud s) {
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
