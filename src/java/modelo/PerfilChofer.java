package modelo;
// Generated May 4, 2016 9:25:30 PM by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * PerfilChofer generated by hbm2java
 */
public class PerfilChofer implements java.io.Serializable {

    private int idPchofer;
    private Chofer chofer;
    private String cfoto;
    private String csobreMi;
    private Boolean cestado;
    private Date fechaCreacion;

    public PerfilChofer() {
    }

    public PerfilChofer(int idPchofer, Chofer chofer, Date fechaCreacion) {
        this.idPchofer = idPchofer;
        this.chofer = chofer;
        this.fechaCreacion = fechaCreacion;
    }

    public PerfilChofer(int idPchofer, Chofer chofer, String cfoto, String csobreMi, Boolean cestado, Date fechaCreacion) {
        this.idPchofer = idPchofer;
        this.chofer = chofer;
        this.cfoto = cfoto;
        this.csobreMi = csobreMi;
        this.cestado = cestado;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdPchofer() {
        return this.idPchofer;
    }

    public void setIdPchofer(int idPchofer) {
        this.idPchofer = idPchofer;
    }

    public Chofer getChofer() {
        return this.chofer;
    }

    public void setChofer(Chofer chofer) {
        this.chofer = chofer;
    }

    public String getCfoto() {
        return this.cfoto;
    }

    public void setCfoto(String cfoto) {
        this.cfoto = cfoto;
    }

    public String getCsobreMi() {
        return this.csobreMi;
    }

    public void setCsobreMi(String csobreMi) {
        this.csobreMi = csobreMi;
    }

    public Boolean getCestado() {
        return this.cestado;
    }

    public void setCestado(Boolean cestado) {
        this.cestado = cestado;
    }

    public Date getFechaCreacion() {
        return this.fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

}
