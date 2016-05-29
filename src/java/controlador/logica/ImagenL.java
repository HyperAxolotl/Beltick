package controlador.logica;

import modelo.ConexionBD;
import modelo.Imagen;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ImagenL {
    
    private Session con;
    
    public byte[] getImagen(int idImagen) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Imagen img = (Imagen)con.get(Imagen.class,idImagen);
        con.close();
        return img.getImagen();
    }
}
