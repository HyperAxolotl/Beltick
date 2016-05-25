package modelo;
// Generated May 7, 2016 1:06:44 AM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Automovil generated by hbm2java
 */
@Entity
@Table(name = "automovil", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = "id_chofer"),
    @UniqueConstraint(columnNames = "no_identificacion"),
    @UniqueConstraint(columnNames = "placa")}
)
public class Automovil implements java.io.Serializable {

    private int idAutomovil;
    private Chofer chofer;
    private String noIdentificacion;
    private String modelo;
    private String placa;
    private String color;
    private Integer capacidad;
    private Set rutas = new HashSet(0);

    public Automovil() {
    }

    public Automovil(int idAutomovil, Chofer chofer, String noIdentificacion, String modelo, String placa, String color, Integer capacidad) {
        this.idAutomovil = idAutomovil;
        this.chofer = chofer;
        this.noIdentificacion = noIdentificacion;
        this.modelo = modelo;
        this.placa = placa;
        this.color = color;
        this.capacidad = capacidad;
    }

    public Automovil(int idAutomovil, Chofer chofer, String noIdentificacion, String modelo, String placa, String color, Integer capacidad, Set rutas) {
        this.idAutomovil = idAutomovil;
        this.chofer = chofer;
        this.noIdentificacion = noIdentificacion;
        this.modelo = modelo;
        this.placa = placa;
        this.color = color;
        this.capacidad = capacidad;
        this.rutas = rutas;
    }

    @Id

    @Column(name = "id_automovil", unique = true, nullable = false)
    public int getIdAutomovil() {
        return this.idAutomovil;
    }

    public void setIdAutomovil(int idAutomovil) {
        this.idAutomovil = idAutomovil;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_chofer", unique = true, nullable = false)
    public Chofer getChofer() {
        return this.chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    @Column(name = "no_identificacion", unique = true, nullable = false, length = 64)
    public String getNoIdentificacion() {
        return this.noIdentificacion;
    }

    public void setNoIdentificacion(String noIdentificacion) {
        this.noIdentificacion = noIdentificacion;
    }

    @Column(name = "modelo", nullable = false, length = 64)
    public String getModelo() {
        return this.modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    @Column(name = "placa", unique = true, nullable = false, length = 8)
    public String getPlaca() {
        return this.placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    @Column(name = "color", nullable = false, length = 32)
    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Column(name = "capacidad", nullable = false)
    public Integer getCapacidad() {
        return this.capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "automovil")
    public Set getRutas() {
        return this.rutas;
    }

    public void setRutas(Set rutas) {
        this.rutas = rutas;
    }

}
