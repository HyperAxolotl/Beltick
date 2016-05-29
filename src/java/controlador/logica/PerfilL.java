/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;

import java.util.List;
import modelo.Automovil;
import modelo.Chofer;
import modelo.ConexionBD;
import modelo.Imagen;
import modelo.Pasajero;
import modelo.PerfilChofer;
import modelo.PerfilPasajero;
import modelo.Ruta;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;

/**
 *
 * @author hyperaxolotl
 */
public class PerfilL {

    private Session con;

    public List<PerfilPasajero> listaPasajeros() {
        Criteria c;
        List<PerfilPasajero> lstPasajeros = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            c = con.createCriteria(PerfilPasajero.class);
            c.addOrder(Order.asc("idPpasajero"));
            lstPasajeros = c.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //con.close();
            return lstPasajeros;
        }
    }

    public List<PerfilChofer> listaChoferes() {
        Criteria c;
        List<PerfilChofer> lstChoferes = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            c = con.createCriteria(PerfilChofer.class);
            c.addOrder(Order.asc("idPchofer"));
            lstChoferes = c.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //con.close();
            return lstChoferes;
        }

    }

    public boolean verificarPasajero(Chofer chofer, Pasajero p) {
        boolean b = false;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("from Chofer c join c.automovils a join a.rutas r "
                    + "join r.pasajeroRutas pr where c.idChofer = :idC and pr.pasajero.idPasajero = :idP");
            query.setParameter("idC", chofer.getIdChofer());
            query.setParameter("idP", p.getIdPasajero());
            List<?> list = query.list();
            b = list.size() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return b;
        }
    }

}
