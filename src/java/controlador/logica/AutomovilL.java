package controlador.logica;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Automovil;
import org.hibernate.Transaction;


public class AutomovilL implements Serializable{
    private Session con;
    private Transaction trans;
    


    
    public FacesMessage registrar(Automovil a, String confirmacion){
        FacesMessage mensaje = null;
        System.out.println("\n\n\n\n\nComienza registro de automovil");
        try{
            con = ConexionBD.getSessionFactory().openSession();
            System.out.println("Conexion realizada");
            trans = con.beginTransaction();
            con.save(a);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error con el registro del pasajero", null);
            System.out.println("Esta es la excepcion "+e.getClass().getName());
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
     
    }
   
}