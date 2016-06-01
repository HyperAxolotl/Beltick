package controlador.logica;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.Boletin;
import modelo.ConexionBD;
import modelo.Horario;
import modelo.Ruta;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class BoletinL implements Serializable {

    private Session con;
    private Transaction trans;
    private List<Boletin> lstBoletines;

    public List<Boletin> listar(int idRuta) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from Boletin b where b.ruta.idRuta = :id order by b.fecha desc");
            query.setParameter("id", idRuta);
            lstBoletines = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstBoletines;
    }
    
    public FacesMessage publicar(Boletin b, Ruta r) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            Date fecha = new Date();
            b.setFecha(fecha);
            b.setRuta(r);
            con.save(b);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error con el registro de la ruta", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }

    }

    public FacesMessage eliminaPublicacion(Boletin b) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            con.delete(b);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al borrar publicación", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }
}
