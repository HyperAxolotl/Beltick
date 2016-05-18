package controlador.logica;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Chofer;
import modelo.PerfilChofer;
import org.hibernate.Query;
import org.hibernate.Transaction;
import utiles.Cripta;


public class ChoferL implements Serializable{
    
    private Session con;
    private Transaction trans;
    private Cripta cripta;
    
    public FacesMessage registrar(Chofer c, String confirmacion){
        FacesMessage mensaje = null;
        if(!confirmacion.equals(c.getCcontrasenia())) 
                return new FacesMessage(FacesMessage.SEVERITY_INFO, "Las contraseñas no coinciden", null);
        System.out.println("\n\n\n\n\nComienza registro de chofer");
        try{
            cripta = new Cripta();
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            System.out.println("Conexion realizada");
            trans = con.beginTransaction();
            c.setCcontrasenia(cripta.encripta(confirmacion));
            con.save(c);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error con el registro del chofer", null);
            System.out.println("Esta es la excepcion "+e.getClass().getName());
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
     
    }
    
    public PerfilChofer getPerfilChofer(int id) {
        PerfilChofer pc = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            String hql = "FROM PerfilChofer WHERE chofer.idChofer= '" + id + "'";
            Query query = con.createQuery(hql);
            if (!query.list().isEmpty()) {
                pc = (PerfilChofer) query.list().get(0);
            }
        } catch (Exception e) {
            System.err.println("Error con la obtención del perfil del chofer para la obtencion de la clave autogenerada de activacion de cuenta.");
        }
        return pc;
    }
}