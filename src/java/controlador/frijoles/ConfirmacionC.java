/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.frijoles;

import controlador.logica.ConfirmacionL;
import java.io.Serializable;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author carlos
 */
@Named(value = "confirmacionC")
@RequestScoped
public class ConfirmacionC implements Serializable {
    private boolean  tipo;
    private int idPerfil;
    private String clave; 
    private ConfirmacionL ayudante = new ConfirmacionL();
    private FacesMessage mensaje;
    /**
     * Creates a new instance of ConfirmacionC
     */
    public ConfirmacionC() {
    }
    
    public void init(){
        System.out.println("Inicializando frijol de confirmación...");
        tipo = Boolean.parseBoolean(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("t"));
        idPerfil = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idP"));
        clave = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("key");
        System.out.println("Tipo: "+tipo+"\tID: "+idPerfil+"\tClave: "+clave);
        mensaje = ayudante.activaPerfil(idPerfil,tipo,clave);
        FacesContext.getCurrentInstance().addMessage(Integer.toString(idPerfil), mensaje);
    }
    
    public String getNombre() {
        return ayudante.getNombre();
    }

}
