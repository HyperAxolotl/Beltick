/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador.logica;

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
    
}
