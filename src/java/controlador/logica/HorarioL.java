package controlador.logica;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import modelo.Chofer;
import modelo.ConexionBD;
import modelo.Horario;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import utiles.Cripta;

public class HorarioL implements Serializable {

    private Session con;
    private Transaction trans;

    public FacesMessage actualizarHorario(Horario h) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            con.update(h);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al actualizar horario", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }

    public Horario getHorario(int rutaId) {
        Horario a = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM Horario WHERE ruta.idRuta= :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", rutaId);
            if (!query.list().isEmpty()) {
                a = (Horario) query.list().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }

    public String formateaHora(Date hora) {
        String s = "Inactivo";
        if (hora != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            s = sdf.format(hora);
        }
        return s;
    }

    public String formateaFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(fecha);
    }
}
