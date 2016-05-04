package controlador.logica;

import modelo.Chofer;
import modelo.ConexionBD;
import org.hibernate.Query;
import org.hibernate.Session;

import modelo.Pasajero;
import utiles.Cripta;
import utiles.HibernateUtil;

public class SesionL {

    private Cripta cripta;
    private Session con;

    public Pasajero verificarDatos(Pasajero p) throws Exception {
        cripta = new Cripta();
        Pasajero pa = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
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
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            String hql = "FROM Chofer WHERE Ccorreo = '" + c.getCcorreo()
                    + "' and Ccontrasenia = '" + cripta.encripta(c.getCcontrasenia()) + "'";
            Query query = con.createQuery(hql);
            if (!query.list().isEmpty()) {
                ch = (Chofer) query.list().get(0);
            }

        } catch (Exception e) {
            throw e;
        }

        return ch;
    }

}
