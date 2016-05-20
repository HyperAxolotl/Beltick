/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;

import java.util.List;
import modelo.Chofer;
import modelo.ConexionBD;
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

    public Pasajero getPasajero(int id) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Pasajero p = (Pasajero) con.get(Pasajero.class, id);
        con.close();
        return p;
    }

    public Chofer getChofer(int id) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Chofer ch = (Chofer) con.get(Chofer.class, id);
        con.close();
        return ch;
    }

    public boolean tieneRuta(Chofer c) {
        boolean b = false;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select r from Chofer c, Automovil, Ruta r where c.idChofer = :id");
            query.setParameter("id", c.getIdChofer());
            List<?> list = query.list();
            b = !list.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return b;
        }
    }

    public int getIdRuta(Chofer c) {
        int id = -1;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select r.idRuta from Chofer c join c.automovils a join a.rutas r where c.idChofer = :id");
            query.setParameter("id", c.getIdChofer());
            List<?> list = query.list();
            if (!list.isEmpty()) {
                id = (Integer) list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return id;
        }
    }

}
