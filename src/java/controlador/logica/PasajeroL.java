package controlador.logica;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import modelo.Chofer;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Pasajero;
import modelo.PasajeroRuta;
import modelo.PerfilPasajero;
import modelo.Solicitud;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.hibernate.Transaction;
import utiles.Cripta;


public class PasajeroL implements Serializable{
    
    private Session con;
    private Transaction trans;
    private PerfilPasajero perfil = new PerfilPasajero();
    private Cripta cripta;
    private List<PasajeroRuta> listaRutas;
       
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
    
    public FacesMessage actualizarPasajero(Pasajero p) {
        FacesMessage mensaje = null;
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            con.merge(p);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al actualizar perfil", null);
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
    }
    
    public List<PasajeroRuta> listaRutas(Pasajero pasajero) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM PasajeroRuta WHERE pasajero.idPasajero= :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", pasajero.getIdPasajero());
            listaRutas = query.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return listaRutas;
        }
    }
    
    public String getHora(PasajeroRuta pr) {
        Date d = null;
        String st = "";
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select h." + StringUtils.stripAccents(pr.getId().getDia()).toLowerCase()
                    + " from PasajeroRuta pr join pr.ruta r join r.horarios h where r.idRuta = :idruta");
            query.setParameter("idruta", pr.getId().getIdRuta());
            List<Date> list = query.list();
            d = list.get(0);
            if (d != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                st = sdf.format(d);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return st;
        }
    }
    
    public int getChoferId(PasajeroRuta pr) {
        int id = 0;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select c from PasajeroRuta pr join "
                    + "pr.ruta r join r.automovil a join a.chofer c where r.idRuta = :idruta");
            query.setParameter("idruta", pr.getId().getIdRuta());
            List<Chofer> list = query.list();
            id = list.get(0).getIdChofer();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return id;
        }
    }
    
    public FacesMessage eliminaRuta(PasajeroRuta pr) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            Query query = con.createQuery("delete from PasajeroRuta where "
                    + "id.idPasajero = :idP and id.dia = :dia and id.idRuta = :idR");
            query.setParameter("idP", pr.getId().getIdPasajero());
            query.setParameter("dia", pr.getId().getDia());
            query.setParameter("idR", pr.getId().getIdRuta());
            query.executeUpdate();
            trans.commit();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Ruta eliminada", null);
        } catch (Exception e) {
            mensaje = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar ruta", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }
}
