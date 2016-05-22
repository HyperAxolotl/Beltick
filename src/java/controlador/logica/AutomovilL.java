package controlador.logica;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Automovil;
import modelo.Chofer;
import modelo.Horario;
import org.hibernate.Query;
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
    
//    public Automovil getAutomovil(int id) {
//        Automovil a = null;
//        try{
//            if (con == null || !con.isOpen())
//                con = ConexionBD.getSessionFactory().openSession();
//            a = (Automovil)con.get(Automovil.class,id);
//        }catch(Exception e){
//            e.printStackTrace();
//        }finally{
//            con.close();
//            return a;
//        }
//    }
    
    public Automovil getAutomovil(int choferId) {
        Automovil a = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            String hql = "FROM Automovil WHERE chofer.idChofer= :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", choferId);
            if (!query.list().isEmpty()) {
                a = (Automovil) query.list().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }
    
    public FacesMessage actualizarAutomovil(Automovil a) {
        FacesMessage mensaje = null;
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            a.setPlaca(a.getPlaca().toUpperCase());
            con.update(a);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al actualizar datos del automóvil", null);
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
    }
   
}