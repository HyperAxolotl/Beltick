/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;

import modelo.Chofer;
import org.hibernate.Query;
import org.hibernate.Session;

import modelo.Pasajero;
import utiles.Cripta;
import utiles.HibernateUtil;

/**
 *
 * @author diana
 */
public class SesionL {
    private Cripta cripta;
    private Session sesion;

	public Pasajero verificarDatos(Pasajero p) throws Exception {
                cripta = new Cripta();
		Pasajero pa = null;
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			String hql = "FROM Pasajero WHERE Pnombre = '" + p.getPnombre()
					+ "' and Pcontrasenia = '" + cripta.encripta(p.getPcontrasenia()) + "'";
			Query query = sesion.createQuery(hql);
			if (!query.list().isEmpty())
				pa = (Pasajero) query.list().get(0);			

		} catch (Exception e) {
			throw e;
		}

		return pa;
	}

    public Chofer verificarDatos(Chofer c) throws Exception {
         cripta = new Cripta();
		Chofer ch = null;
		try {
			sesion = HibernateUtil.getSessionFactory().openSession();
			String hql = "FROM Chofer WHERE Cnombre = '" + c.getCnombre()
					+ "' and Ccontrasenia = '" + cripta.encripta(c.getCcontrasenia()) + "'";
			Query query = sesion.createQuery(hql);
			if (!query.list().isEmpty())
				ch = (Chofer) query.list().get(0);			

		} catch (Exception e) {
			throw e;
		}

		return ch;
    }
    
}
