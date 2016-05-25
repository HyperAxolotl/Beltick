/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.frijoles;

import java.io.Serializable;
import controlador.logica.RutaL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import modelo.Horario;
import modelo.Pasajero;
import modelo.PasajeroRuta;
import modelo.Ruta;
import modelo.Solicitud;
import org.apache.commons.lang3.StringUtils;

@ManagedBean
@ViewScoped
public class SolicitarServicioC implements Serializable {

    private Ruta ruta;
    private RutaL ayudante;
    private String[] diasSeleccionados;
    private Solicitud solicitud;
    private FacesMessage mensaje;
    private Horario horario;
    private boolean exito;

    public SolicitarServicioC() {
        horario = new Horario();
        ruta = new Ruta();
        ayudante = new RutaL();
        solicitud = new Solicitud();
        diasSeleccionados = new String[6];
    }

    public Horario getHorario() {
        return horario;
    }

    public boolean isExito() {
        return exito;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public Solicitud getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Solicitud solicitud) {
        this.solicitud = solicitud;
    }

    public String solicitar(Pasajero pasajero) throws Exception {
        mensaje = ayudante.solicitar(pasajero, ruta, diasSeleccionados);
        if (mensaje != null) {
            FacesContext.getCurrentInstance().addMessage(null, mensaje);
            return "";
        }
        mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tu solicitud fue enviada con éxito... Espera la confirmación del chofer", null);
        FacesContext.getCurrentInstance().addMessage(null, mensaje);
        exito = true;
        return "";
    }

    //@PostConstruct
    public void init() {
        ruta = ayudante.getRuta(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("rutaId")));
        horario = (Horario) ruta.getHorarios().iterator().next();
    }

    public String[] getDiasSeleccionados() {
        return diasSeleccionados;
    }

    public void setDiasSeleccionados(String[] diasSeleccionados) {
        this.diasSeleccionados = diasSeleccionados;
    }

    public Ruta getRuta() {
        return ruta;
    }

    public void setRuta(Ruta ruta) {
        this.ruta = ruta;
    }

    public String formateaHora(Date hora) {
        String s = "No disponible";
        if (hora != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            s = sdf.format(hora);
        }
        return s;
    }
    
    //Regresa true si un pasajero ya tiene una ruta registrada para el dia recibido como parametro
    //o una solicitud para ese pasajero ese mismo dia
    public boolean noDisponible(Pasajero pasajero, String dia) {
        boolean b = false;
        Set s = pasajero.getPasajeroRutas();
        for(Object o : s) {
            PasajeroRuta pr = (PasajeroRuta)o;
            if(StringUtils.stripAccents(pr.getId().getDia()).toLowerCase().equals(dia))
                b = true;
        }
        s = pasajero.getSolicituds();
        for(Object o : s) {
            Solicitud pr = (Solicitud)o;
            if(StringUtils.stripAccents(pr.getId().getDia()).toLowerCase().equals(dia)
                    && pr.getId().getIdRuta() == horario.getRuta().getIdRuta())
                b = true;
        }
        return b;
    }

}
