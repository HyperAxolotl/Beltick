package modelo;
// Generated May 16, 2016 1:45:53 AM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Imagen generated by hbm2java
 */
@Entity
@Table(name = "imagen", schema = "public"
)
public class Imagen implements java.io.Serializable {

    private int idImagen;
    private String nombre;
    private byte[] imagen;
    private Set perfilChofers = new HashSet(0);
    private Set perfilPasajeros = new HashSet(0);

    public Imagen() {
    }

    public Imagen(int idImagen, byte[] imagen) {
        this.idImagen = idImagen;
        this.imagen = imagen;
    }

    public Imagen(int idImagen, String nombre, byte[] imagen, Set perfilChofers, Set perfilPasajeros) {
        this.idImagen = idImagen;
        this.nombre = nombre;
        this.imagen = imagen;
        this.perfilChofers = perfilChofers;
        this.perfilPasajeros = perfilPasajeros;
    }

    @Id

    @Column(name = "id_imagen", unique = true, nullable = false)
    public int getIdImagen() {
        return this.idImagen;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    @Column(name = "nombre", length = 128)
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Column(name = "imagen", nullable = false)
    public byte[] getImagen() {
        return this.imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "imagen")
    public Set getPerfilChofers() {
        return this.perfilChofers;
    }

    public void setPerfilChofers(Set perfilChofers) {
        this.perfilChofers = perfilChofers;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "imagen")
    public Set getPerfilPasajeros() {
        return this.perfilPasajeros;
    }

    public void setPerfilPasajeros(Set perfilPasajeros) {
        this.perfilPasajeros = perfilPasajeros;
    }

}
