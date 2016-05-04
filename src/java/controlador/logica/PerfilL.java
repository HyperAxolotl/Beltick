/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;

import java.util.List;
import modelo.ConexionBD;
import modelo.PerfilChofer;
import modelo.PerfilPasajero;
import org.hibernate.Criteria;
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
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
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
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
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
    
    
    
    
}
