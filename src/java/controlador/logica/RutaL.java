package controlador.logica;

import java.io.Serializable;
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
}
