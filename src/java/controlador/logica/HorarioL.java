/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Horario;
import org.hibernate.Transaction;
/**
 *
 * @author diana
 */
public class HorarioL implements Serializable{
    
    private Session con;
    private Transaction trans;
    
    public FacesMessage registrar(Horario a, String confirmacion){
        FacesMessage mensaje = null;
        System.out.println("\n\n\n\n\nComienza solicitar servicio");
        try{
            con = ConexionBD.getSessionFactory().openSession();
            System.out.println("Conexion realizada");
            trans = con.beginTransaction();
            //a.setChofer(chofer);
            con.save(a);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error con solicitar servicio", null);
            System.out.println("Esta es la excepcion " + e.getClass().getName());
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
     
    }
   
}
    

