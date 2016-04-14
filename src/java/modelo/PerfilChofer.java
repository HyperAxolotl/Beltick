package modelo;
// Generated 11/04/2016 03:54:24 PM by Hibernate Tools 4.3.1



/**
 * PerfilChofer generated by hbm2java
 */
public class PerfilChofer  implements java.io.Serializable {


     private int idPchofer;
     private Chofer chofer;
     private String cfoto;
     private String csobreMi;
     private Boolean cestado;

    public PerfilChofer() {
    }

	
    public PerfilChofer(Chofer chofer) {
        this.chofer = chofer;
    }
    public PerfilChofer(Chofer chofer, String cfoto, String csobreMi, Boolean cestado) {
       this.chofer = chofer;
       this.cfoto = cfoto;
       this.csobreMi = csobreMi;
       this.cestado = cestado;
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




}


