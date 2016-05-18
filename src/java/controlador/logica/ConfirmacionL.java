/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;

import javax.faces.application.FacesMessage;
import modelo.ConexionBD;
import modelo.PerfilChofer;
import modelo.PerfilPasajero;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author carlos
 */
public class ConfirmacionL {

    private Session con;
    private Transaction trans;
    private PerfilPasajero pp;
    private PerfilChofer pc;
    private String nombre;
    boolean exito;

    public FacesMessage activaPerfil(int id, boolean tipo, String clave) {
        FacesMessage resultado = null;
        if (tipo) {
            exito = setPerfilChofer(id,clave);
            if (!exito) {
                resultado = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error con la activación de la cuenta o ya activada", null);
                return resultado;
            }
            nombre = pc.getChofer().getCnombre() + " " + pc.getChofer().getCapp() + " " + pc.getChofer().getCapm();
            System.out.println("\n\n\n\n\nComienza activacion de perfil para "+nombre);
            pc.setCestado(true);
            try {
                if (con == null || !con.isOpen())
                    con = ConexionBD.getSessionFactory().openSession();
                trans = con.beginTransaction();
                con.update(pc);
                trans.commit();
                resultado = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activación de la cuenta exitosa", null);
            } catch (Exception e) {
                trans.rollback();
            } 
        } else {
            exito = setPerfilPasajero(id,clave);
            if (!exito) {
                resultado = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error con la activación de la cuenta o ya activada", null);
                return resultado;
            }
            nombre = pp.getPasajero().getPnombre() + " " + pp.getPasajero().getPapp() + " " + pp.getPasajero().getPapm();
            System.out.println("\n\n\n\n\nComienza activacion de perfil para "+nombre);
            pp.setPestado(true);
            try {
                if (con == null || !con.isOpen())
                    con = ConexionBD.getSessionFactory().openSession();
                trans = con.beginTransaction();
                con.update(pp);
                trans.commit();
                resultado = new FacesMessage(FacesMessage.SEVERITY_INFO, "Activación de la cuenta exitosa", null);
            } catch (Exception e) {
                trans.rollback();
            }
        }
        con.close();
        return resultado;

    }

    private boolean setPerfilChofer(int id, String clave) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM PerfilChofer WHERE idPchofer= '" + id + "' and clave = '"+clave+"' and cestado = false";
            Query query = con.createQuery(hql);
            if (!query.list().isEmpty()) 
                pc = (PerfilChofer) query.list().get(0);
            else
                throw new Exception();
        } catch (Exception e) {
            System.err.println("Error con la obtención del perfil del chofer para la obtencion de la clave autogenerada de activacion de cuenta.");
            return false;
        }
        return true;
    }

    private boolean setPerfilPasajero(int id, String clave) {
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM PerfilPasajero WHERE idPpasajero = '" + id + "' and clave = '"+clave+"' and pestado = false";
            Query query = con.createQuery(hql);
            if (!query.list().isEmpty()) 
                pp = (PerfilPasajero) query.list().get(0);
            else
                throw new Exception();
        } catch (Exception e) {
            System.err.println("Error con la obtención del perfil del pasajero para la obtencion de la clave autogenerada de activacion de cuenta.");
            return false;
        }
        return true;
    }

    public String getNombre() {
        return nombre; 
    }
}
