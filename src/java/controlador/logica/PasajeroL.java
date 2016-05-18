package controlador.logica;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Pasajero;
import modelo.PerfilPasajero;
import org.hibernate.Query;
import org.hibernate.Transaction;
import utiles.Cripta;


public class PasajeroL implements Serializable{
    private Session con;
    private Transaction trans;
    private PerfilPasajero perfil = new PerfilPasajero();
    private Cripta cripta;
       
    public FacesMessage registrar(Pasajero p, String confirmacion){
        FacesMessage mensaje = null;
        if(!confirmacion.equals(p.getPcontrasenia())) 
                return new FacesMessage(FacesMessage.SEVERITY_INFO, "Las contraseñas no coinciden", null);
        System.out.println("\n\n\n\n\nComienza registro de pasajero");
        try{
            cripta = new Cripta();
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            System.out.println("Conexion realizada");
            trans = con.beginTransaction();
            p.setPcontrasenia(cripta.encripta(confirmacion));
            con.save(p);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error con el registro del pasajero", null);
            System.out.println("Esta es la excepcion "+e.getClass().getName());
            e.printStackTrace();
        }finally{
            //con.update(p);
            con.close();
            return mensaje;
        }
    }
    
    public PerfilPasajero getPerfilPasajero(int id) {
        PerfilPasajero pp = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            String hql = "FROM PerfilPasajero WHERE pasajero.idPasajero = '" + id + "'";
            Query query = con.createQuery(hql);
            if (!query.list().isEmpty()) {
                pp = (PerfilPasajero) query.list().get(0);
            }
        } catch (Exception e) {
            System.err.println("Error con la obtención del perfil del pasajero para la obtencion de la clave autogenerada de activacion de cuenta.");
        }
        return pp;
    }
}
