package controlador.frijoles;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

import modelo.Ruta;
import controlador.logica.RutaL;
import java.util.List;
import javax.faces.bean.ViewScoped;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
@ViewScoped
public class RutaC {
    
    private Ruta ruta = new Ruta();
    private RutaL rutaL = new RutaL();
    private List<Ruta> lstRutas;
    
    public List<Ruta> getLstRutas() {
        return lstRutas;
    }
    
    public void listar() throws Exception {
        lstRutas = rutaL.listar();
    }
    
}
