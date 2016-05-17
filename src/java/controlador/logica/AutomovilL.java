package controlador.logica;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Automovil;
import modelo.Chofer;
import org.hibernate.Transaction;

public class AutomovilL implements Serializable{
    
    private Session con;
    private Transaction trans;
    
    public FacesMessage registrar(Automovil a, Chofer c){
        FacesMessage mensaje = null;
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            a.setPlaca(a.getPlaca().toUpperCase());
            c.getAutomovils().add(a);
            con.save(a);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error con el registro del automovil", null);
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
     
    }
   
}