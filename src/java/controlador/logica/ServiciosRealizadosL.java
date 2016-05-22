/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;

import java.util.List;
import modelo.Chofer;

import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Pasajero;
import org.hibernate.Query;
import org.hibernate.Transaction;

/**
 *
 * @author diana
 */
public class ServiciosRealizadosL {
    
    private List<Pasajero> lpasajeros;
    private Session con;
    private Transaction trans;
    
    

    public List<Pasajero> lista(Chofer chofer) {
      try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query;
          query = con.createQuery("select distintc pasajero from Chofer c,Automovil,Ruta ,PasajeroRuta per where c.idChofer = :id  ");
            query.setParameter("id", chofer.getIdChofer());
            lpasajeros= query.list();
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        } 
            return lpasajeros;
        
    }

}
