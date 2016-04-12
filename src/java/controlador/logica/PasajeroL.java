/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;
import java.io.Serializable;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Pasajero;
import org.hibernate.Transaction;


/**
 *
 * @author diana
 */
public class PasajeroL implements Serializable{
    private Session con;
    private Transaction trans;
    
    public boolean registrar(Pasajero p){
        boolean exito = false;
        
        try{
            con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            con.save(p);
            trans.commit();
            exito = true;
        }catch(Exception e){
            trans.rollback();
            e.printStackTrace();
        }finally{
            con.close();
            return exito;
        }
     
    }
}
