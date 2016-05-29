package controlador.logica;

import java.util.List;
import javax.faces.context.FacesContext;
import modelo.Automovil;
import modelo.Chofer;
import modelo.ConexionBD;
import modelo.NotificacionChofer;
import modelo.NotificacionPasajero;
import org.hibernate.Query;
import org.hibernate.Session;

import modelo.Pasajero;
import org.hibernate.Transaction;
import utiles.Cripta;

public class SesionL {

    private Cripta cripta;
    private Session con;
    private Transaction trans;

    public Pasajero verificarDatos(Pasajero p) throws Exception {
        cripta = new Cripta();
        Pasajero pa = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM Pasajero WHERE Pcorreo = '" + p.getPcorreo()
                    + "' and Pcontrasenia = '" + cripta.encripta(p.getPcontrasenia()) + "'";
            Query query = con.createQuery(hql);
            if (!query.list().isEmpty()) {
                pa = (Pasajero) query.list().get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return pa;
    }

    public Chofer verificarDatos(Chofer c) throws Exception {
        cripta = new Cripta();
        Chofer ch = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM Chofer WHERE Ccorreo = '" + c.getCcorreo()
                    + "' and Ccontrasenia = '" + cripta.encripta(c.getCcontrasenia()) + "'";
            Query query = con.createQuery(hql);
            if (!query.list().isEmpty()) {
                ch = (Chofer) query.list().get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return ch;
    }

    public int notificacionChoferVista(Chofer chofer) {
        int i = 0;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "UPDATE NotificacionChofer set visto = 'true' WHERE "
                    + "visto = false AND chofer.idChofer = :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", chofer.getIdChofer());
            trans = con.beginTransaction();
            i = query.executeUpdate();
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
        } finally {
            con.close();
            return i;
        }
    }

    public int notificacionPasajeroVista(Pasajero pasajero) {
        int i = 0;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "UPDATE NotificacionPasajero set visto = 'true' WHERE "
                    + "visto = false AND pasajero.idPasajero = :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", pasajero.getIdPasajero());
            trans = con.beginTransaction();
            i = query.executeUpdate();
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
        } finally {
            con.close();
            return i;
        }
    }

    public boolean verificarNotificacionesChofer(Chofer chofer) {
        boolean b = false;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "SELECT n FROM Chofer c join c.notificacionChofers n WHERE c.idChofer = :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", chofer.getIdChofer());
            List<NotificacionChofer> l = query.list();
            if (!l.isEmpty()) {
                for (NotificacionChofer n : l) {
                    if (!n.isVisto()) {
                        b = true;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return b;
        }
    }

    public boolean verificarNotificacionesPasajero(Pasajero pasajero) {
        boolean b = false;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "SELECT n FROM Pasajero p join p.notificacionPasajeros n WHERE p.idPasajero = :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", pasajero.getIdPasajero());
            List<NotificacionPasajero> l = query.list();
            if (!l.isEmpty()) {
                for (NotificacionPasajero n : l) {
                    if (!n.isVisto()) {
                        b = true;
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return b;
        }
    }

    public Automovil cAuto(Chofer chofer) {
        Automovil a = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "SELECT a FROM Chofer c join c.automovils a WHERE c.idChofer = :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", chofer.getIdChofer());
            List<Automovil> l = query.list();
            if (!l.isEmpty()) {
                a = l.get(0);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }

    public boolean tieneAuto(Chofer chofer) {
        boolean b = false;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM Chofer c join c.automovils WHERE c.idChofer = :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", chofer.getIdChofer());
            List<?> l = query.list();
            if (!l.isEmpty()) {
                b = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return b;
        }
    }

}
