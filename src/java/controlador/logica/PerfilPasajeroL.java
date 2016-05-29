package controlador.logica;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import modelo.ConexionBD;
import modelo.Imagen;
import modelo.Pasajero;
import modelo.PerfilChofer;
import modelo.PerfilPasajero;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PerfilPasajeroL implements Serializable {

    private Session con;
    private Transaction trans;

    public PerfilPasajero getPerfilPasajero(int pasajeroId) {
        PerfilPasajero a = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM PerfilPasajero WHERE pasajero.idPasajero= :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", pasajeroId);
            if (!query.list().isEmpty()) {
                a = (PerfilPasajero) query.list().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }

    public FacesMessage actualizarPerfilPasajero(PerfilPasajero p, Imagen i) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            if (i != null) {
                Query query = con.createQuery("select img from PerfilPasajero p join p.imagen "
                        + "img where p.idPpasajero = " + p.getIdPpasajero());
                List<Imagen> l = query.list();
                if (!l.isEmpty()) {
                    con.delete(l.get(0));
                }
                con.save(i);
                p.setImagen(i);
            }
            con.update(p);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al actualizar perfil", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }
    
    public Pasajero getPasajero(int id) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Pasajero p = (Pasajero) con.get(Pasajero.class, id);
        con.close();
        return p;
    }
        
    public Imagen getImagenPasajero(int idPasajero){
         if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Query query = con.createQuery("select img from Pasajero p join p.perfilPasajeros pp join pp.imagen "
                + "img where p.idPasajero = " + idPasajero);
        List<Imagen> list = query.list();
        if(list.isEmpty()) {
            con.close();
            return null;
        }
        Imagen img = list.get(0);
        con.close();
        return img;
    }


}
