package controlador.logica;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import modelo.ConexionBD;
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
    private List<Solicitud> lstRutas;

    public List<Solicitud> listar() throws Exception {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Criteria cri = con.createCriteria(Solicitud.class);
            lstRutas = cri.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstRutas;
    }

    public FacesMessage eliminar(Solicitud s) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            con.delete(s);
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

    public FacesMessage registrar(Solicitud s) {
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
            con.delete(s);
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
                    + " from Solicitud, Horario h, Ruta r where r.idRuta = :idruta");
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
            Query query = con.createQuery("select a.capacidad from Automovil a,Ruta r,PasajeroRuta pr "
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
}
