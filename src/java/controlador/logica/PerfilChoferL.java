package controlador.logica;

import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import modelo.Automovil;
import modelo.CalificacionChofer;
import modelo.Chofer;
import modelo.ConexionBD;
import modelo.Horario;
import modelo.PerfilChofer;
import modelo.Ruta;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;

public class PerfilChoferL implements Serializable {
    
    private Session con;
    private Transaction trans;
    
    public PerfilChofer getPerfilChofer(int choferId) {
        PerfilChofer a = null;
        try {
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            String hql = "FROM PerfilChofer WHERE chofer.idChofer= :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", choferId);
            if (!query.list().isEmpty()) {
                a = (PerfilChofer) query.list().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }
    
    public FacesMessage actualizarPerfilChofer(PerfilChofer p) {
        FacesMessage mensaje = null;
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            con.update(p);
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
    
    public Chofer getChofer(int id) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Chofer ch = (Chofer) con.get(Chofer.class, id);
        con.close();
        return ch;
    }
    
    public Automovil getAutomovil(int id){
         if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Query query = con.createQuery("from Chofer where idChofer = "+id);
        List<?> list = query.list();
        if(list.isEmpty()) {
            con.close();
            return null;
        }
        Chofer c = (Chofer)list.get(0);
        Automovil a = (Automovil)c.getAutomovils().iterator().next();
        con.close();
        return a;
    }
    
    public Ruta getRuta(int id){
         if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Query query = con.createQuery("from Ruta where id_automovil = "+id);
        List<?> list = query.list();
        if(list.isEmpty()) {
            con.close();
            System.out.println("No hay ruta para el auto "+id);
            return null;
        }
        Ruta r = (Ruta)list.get(0);
        con.close();
        System.out.println("Ruta para el auto "+id);
        return r;
    }
    
    public boolean tieneRuta(Chofer c) {
        boolean b = false;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select r from Chofer c join c.automovils a join a.rutas r where c.idChofer = :id");
            query.setParameter("id", c.getIdChofer());
            List<?> list = query.list();
            b = !list.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return b;
        }
    }
    
    public int getIdRuta(Chofer c) {
        int id = -1;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select r.idRuta from Chofer c join c.automovils a join a.rutas r where c.idChofer = :id");
            query.setParameter("id", c.getIdChofer());
            List<?> list = query.list();
            if (!list.isEmpty()) {
                id = (Integer) list.get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return id;
        }
    }
    
    public List<CalificacionChofer> listarCalificaciones(int idChofer){
        Criteria c;
        List<CalificacionChofer> lstCalif = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select c from CalificacionChofer c where c.chofer.idChofer = :id");
            query.setParameter("id", idChofer);
            lstCalif = query.list();
            if(lstCalif.isEmpty())
                System.out.println("Está vacía para el id "+idChofer);
            else 
                System.out.println(lstCalif.get(0).getDescripcion());
            System.out.println("Se ha creado la lista de calificaciones");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //con.close();
            return lstCalif;
        }
    }
}
