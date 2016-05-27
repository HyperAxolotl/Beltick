package controlador.frijoles;

import controlador.logica.ImagenL;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@Named(value = "imagenC")
@ManagedBean
@ApplicationScoped
public class ImagenC implements Serializable {

    private ImagenL ayudante;
    
    public ImagenC() {
        ayudante = new ImagenL();
    }
    
    public byte[] getImagen(int idImagen) {
        return ayudante.getImagen(idImagen);
    }
    
}
