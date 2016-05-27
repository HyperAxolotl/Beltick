package controlador.frijoles;

import controlador.logica.PerfilL;
import java.io.Serializable;
import modelo.PerfilPasajero;
import modelo.PerfilChofer;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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

}
