package controlador.logica;

import java.io.Serializable;
import java.util.List;
import modelo.Chofer;
import modelo.ConexionBD;
import modelo.NotificacionChofer;
import modelo.NotificacionPasajero;
import modelo.Pasajero;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class NotificacionL implements Serializable {

    private Session con;
    private Transaction trans;
    private List<NotificacionChofer> lstNotificacionesC;
    private List<NotificacionPasajero> lstNotificacionesP;

    public List<NotificacionChofer> listarChofer(Chofer chofer) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from NotificacionChofer n where n.chofer.idChofer = :id order by n.fecha desc");
            query.setParameter("id", chofer.getIdChofer());
            lstNotificacionesC = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstNotificacionesC;
    }

    public List<NotificacionPasajero> listarPasajero(Pasajero pasajero) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from NotificacionPasajero n where n.pasajero.idPasajero = :id order by n.fecha desc");
            query.setParameter("id", pasajero.getIdPasajero());
            lstNotificacionesP = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstNotificacionesP;
    }
    
}
