package controlador.logica;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import modelo.ConexionBD;
import modelo.Ruta;
import org.hibernate.Criteria;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;


public class RutaL implements Serializable{
    
    private Session con;
    private Transaction trans;
    private List<Ruta> lstRutas;
    
    public List<Ruta> listar() throws Exception {
        try {
            con = ConexionBD.getSessionFactory().openSession();
            Criteria cri = con.createCriteria(Ruta.class);
            lstRutas = cri.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lstRutas;
    }
    
    public FacesMessage registrar(Ruta r){
        FacesMessage mensaje = null;
        try{
            con = ConexionBD.getSessionFactory().openSession();
            trans = con.beginTransaction();
            Date fecha = new Date();
            String mapa = FacesContext.getCurrentInstance().
		getExternalContext().getRequestParameterMap().get("mapa");
            r.setMapa(mapa);
            r.setActiva(true);           
            r.setFechaCreacion(fecha);
            con.save(r);
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
