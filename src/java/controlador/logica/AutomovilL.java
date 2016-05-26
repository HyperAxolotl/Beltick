package controlador.logica;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Automovil;
import modelo.Chofer;
import modelo.Horario;
import modelo.Ruta;
import org.hibernate.Query;
import org.hibernate.Transaction;

public class AutomovilL implements Serializable{
    
    private Session con;
    private Transaction trans;
    
    public FacesMessage registrar(Automovil a, Ruta r, Horario h, Chofer c){
        FacesMessage mensaje = null;
        try{
            if (con == null || !con.isOpen())
                con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            a.setPlaca(a.getPlaca().toUpperCase());
            a.setChofer(c);
            c.getAutomovils().add(a);
            con.save(a);
            Date fecha = new Date();
            String mapa = FacesContext.getCurrentInstance().
                    getExternalContext().getRequestParameterMap().get("mapa");
            r.setMapa(mapa);
            r.setActiva(true);
            r.setFechaCreacion(fecha);
            r.setAutomovil(a);
            con.save(r);
            h.setRuta(r);
            con.save(h);
            trans.commit();
        }catch(Exception e){
            trans.rollback();
            c.getAutomovils().clear();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al registrar los datos", null);
            e.printStackTrace();
        }finally{
            con.close();
            return mensaje;
        }
     
    }
    
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