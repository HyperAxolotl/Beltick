package controlador.logica;
import java.io.Serializable;
import java.util.List;

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
}
