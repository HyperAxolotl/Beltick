package controlador.logica;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import modelo.ConexionBD;
import modelo.Horario;
import modelo.Imagen;
import modelo.PerfilChofer;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PerfilChoferL implements Serializable {

    private Session con;
    private Transaction trans;

    public PerfilChofer getPerfilChofer(int choferId) {
        PerfilChofer a = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM PerfilChofer WHERE chofer.idChofer= :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", choferId);
            if (!query.list().isEmpty()) {
                a = (PerfilChofer) query.list().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }

    public FacesMessage actualizarPerfilChofer(PerfilChofer p, Imagen i) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            if (i != null) {
                Query query = con.createQuery("select img from PerfilChofer p join p.imagen "
                        + "img where p.idPchofer = " + p.getIdPchofer());
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

}
