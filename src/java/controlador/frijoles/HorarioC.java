/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.frijoles;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

import modelo.Horario;
import controlador.logica.HorarioL;
/**
 *
 * @author diana
 */
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import modelo.Automovil;
@ManagedBean
@SessionScoped
public class HorarioC implements Serializable {

    
        private  Horario horario = new Horario();
        private String confirmacion;
        private HorarioL ayudante = new HorarioL();
        private FacesMessage mensaje;
        
    public HorarioC() {
        
    }
    
    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }
    
    public String registro() {
        mensaje = ayudante.registrar(horario,confirmacion);
        if(mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "error";
        }
        return "exito";
    }
    public String getConfirmacion() {
        return confirmacion;
    }
}
    
