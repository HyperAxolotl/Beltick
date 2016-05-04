package controlador.logica;

import java.io.Serializable;
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
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
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
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
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
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
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
}
