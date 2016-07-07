package controlador.frijoles;

import controlador.logica.CalificacionL;
import controlador.logica.PerfilChoferL;
import controlador.logica.PerfilL;
import controlador.logica.PerfilPasajeroL;
import java.io.Serializable;
import modelo.PerfilPasajero;
import modelo.PerfilChofer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Chofer;
import modelo.Imagen;
import modelo.Pasajero;

/**
 *
 * @author hyperaxolotl
 */
@ManagedBean
@ViewScoped
public class PerfilC implements Serializable {

    private List<PerfilPasajero> pasajeros;
    private List<PerfilChofer> choferes;
    private final PerfilL ayudante;

    public PerfilC() {
        ayudante = new PerfilL();
    }

    public List<PerfilChofer> getChoferes() {
        return choferes;
    }

    public List<PerfilPasajero> getPasajeros() {
        return pasajeros;
    }

    public void setChoferes(List<PerfilChofer> choferes) {
        this.choferes = choferes;
    }

    public void setPasajeros(List<PerfilPasajero> pasajeros) {
        this.pasajeros = pasajeros;
    }

    @PostConstruct
    public void listar() {
        choferes = ayudante.listaChoferes();
        pasajeros = ayudante.listaPasajeros();
    }

    public void listarChoferes() {
        choferes = ayudante.listaChoferes();
    }

    public void listarPasajeros() {
        pasajeros = ayudante.listaPasajeros();
    }

    public boolean verificarImagenChofer(int chofer) {
        PerfilChoferL pL = new PerfilChoferL();
        return pL.getImagenChofer(chofer) != null;
    }
    
    public boolean verificarImagenPasajero(int pasajero) {
        PerfilPasajeroL pL = new PerfilPasajeroL();
        return pL.getImagenPasajero(pasajero) != null;
    }
    
    public Imagen getImagenChofer(int chofer) {
        PerfilChoferL pL = new PerfilChoferL();
        return pL.getImagenChofer(chofer);
    }
    
    public Imagen getImagenPasajero(int pasajero) {
        PerfilPasajeroL pL = new PerfilPasajeroL();
        return pL.getImagenPasajero(pasajero);
    }
    
    public int getCalificacionPasajero(Pasajero pasajero) {
        CalificacionL calificacionL = new CalificacionL();
        if (pasajero != null) {
            return calificacionL.getCalificacionPasajero(pasajero);
        }
        return 0;
    }
    
    public int getCalificacionChofer(Chofer chofer) {
        CalificacionL calificacionL = new CalificacionL();
        if (chofer != null) {
            return calificacionL.getCalificacionChofer(chofer);
        }
        return 0;
    }
}
