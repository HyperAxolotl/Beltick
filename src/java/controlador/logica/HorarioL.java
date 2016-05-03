package controlador.logica;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Horario;
import org.hibernate.Transaction;
/**
 *
 * @author hyperaxolotl
 */
public class HorarioL implements Serializable{
    
    private Session con;
    private Transaction trans;
    
    public FacesMessage registrar(Horario h){
        FacesMessage mensaje = null;
        try{
            con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            con.save(h);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error con el registro del horario", null);
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
     
    }
   
}
    

