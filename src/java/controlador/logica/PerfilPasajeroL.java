package controlador.logica;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import modelo.CalificacionPasajero;
import modelo.Chofer;
import modelo.ConexionBD;
import modelo.Horario;
import modelo.Imagen;
import modelo.Pasajero;
import modelo.PerfilChofer;
import modelo.PerfilPasajero;
import modelo.Ruta;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class PerfilPasajeroL implements Serializable {

    private Session con;
    private Transaction trans;

    public boolean tieneRuta(Pasajero p) {
        boolean b = false;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select p from Pasajero p join p.pasajeroRutas pr where p.idPasajero= :id");
            query.setParameter("id", p.getIdPasajero());
            List<?> list = query.list();
            b = !list.isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return b;
        }
    }

    public PerfilPasajero getPerfilPasajero(int pasajeroId) {
        PerfilPasajero a = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            String hql = "FROM PerfilPasajero WHERE pasajero.idPasajero= :id";
            Query query = con.createQuery(hql);
            query.setParameter("id", pasajeroId);
            if (!query.list().isEmpty()) {
                a = (PerfilPasajero) query.list().get(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return a;
        }
    }

    public FacesMessage actualizarPerfilPasajero(PerfilPasajero p, Imagen i) {
        FacesMessage mensaje = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            trans = con.beginTransaction();
            if (i != null) {
                Query query = con.createQuery("select img from PerfilPasajero p join p.imagen "
                        + "img where p.idPpasajero = " + p.getIdPpasajero());
                List<Imagen> l = query.list();
                if (!l.isEmpty()) {
                    con.delete(l.get(0));
                }
                con.save(i);
                p.setImagen(i);
            }
            con.update(p);
            trans.commit();
        } catch (Exception e) {
            trans.rollback();
            mensaje = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error al actualizar perfil", null);
            e.printStackTrace();
        } finally {
            con.close();
            return mensaje;
        }
    }

    public Pasajero getPasajero(int id) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Pasajero p = (Pasajero) con.get(Pasajero.class, id);
        con.close();
        return p;
    }

    public Imagen getImagenPasajero(int idPasajero) {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Query query = con.createQuery("select img from Pasajero p join p.perfilPasajeros pp join pp.imagen "
                + "img where p.idPasajero = " + idPasajero);
        List<Imagen> list = query.list();
        if (list.isEmpty()) {
            con.close();
            return null;
        }
        Imagen img = list.get(0);
        con.close();
        return img;
    }

    //Tener cuidado con el dia de la tabla pasajero_ruta
    public Date horaDia(int id, int dia) {
        List<Date> horas = new LinkedList<>();
        Query query = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            switch (dia) {
                case 1:
                    System.out.println("Obteniendo hora en lunes");
                    query = con.createQuery("select h.lunes from Horario h join h.ruta r join r.pasajeroRutas pr where pr.pasajero.idPasajero = :id and pr.id.dia = 'Lunes'");
                    break;
                case 2:
                    System.out.println("Obteniendo hora en martes");
                    query = con.createQuery("select h.martes from Horario h join h.ruta r join r.pasajeroRutas pr where pr.pasajero.idPasajero = :id and pr.id.dia = 'Martes'");
                    break;
                case 3:
                    System.out.println("Obteniendo hora en miercoles");
                    query = con.createQuery("select h.miercoles from Horario h join h.ruta r join r.pasajeroRutas pr where pr.pasajero.idPasajero = :id and pr.id.dia = 'Miércoles'");
                    break;
                case 4:
                    System.out.println("Obteniendo hora en jueves");
                    query = con.createQuery("select h.jueves from Horario h join h.ruta r join r.pasajeroRutas pr where pr.pasajero.idPasajero = :id and pr.id.dia = 'Jueves'");
                    break;
                case 5:
                    System.out.println("Obteniendo hora en viernes");
                    query = con.createQuery("select h.viernes from Horario h join h.ruta r join r.pasajeroRutas pr where pr.pasajero.idPasajero = :id and pr.id.dia = 'Viernes'");
                    break;
                case 6:
                    System.out.println("Obteniendo hora en sabado");
                    query = con.createQuery("select h.sabado from Horario h join h.ruta r join r.pasajeroRutas pr where pr.pasajero.idPasajero = :id and pr.id.dia = 'Sábado'");
                    break;
                default:
                    break;
            }
            if (query != null) {
                query.setParameter("id", id);
                horas = query.list();
            } else {
                System.out.println("El query resultó shafa");
            }
        } catch (Exception e) {
            System.err.println("Error obteniendo hora del dia " + dia + " para el pasajero " + id);
            e.printStackTrace();
        } finally {
            con.close();
            if (horas.isEmpty()) {
                System.out.println("EL pasajero " + id + " no tiene ruta en dia " + dia);
                return null;
            }
            return horas.get(0);
        }
    }

    public Ruta rutaDia(int id, int dia) {
        Ruta ruta = new Ruta();
        Query query = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            switch(dia){
                case 1:
                    query = con.createQuery("select r FROM Horario h join h.ruta r join r.pasajeroRutas pr WHERE pr.pasajero.idPasajero= :id and pr.id.dia = 'Lunes'");
                    break;
                case 2:
                    query = con.createQuery("select r FROM Horario h join h.ruta r join r.pasajeroRutas pr WHERE pr.pasajero.idPasajero= :id and pr.id.dia = 'Martes'");
                    break;
                case 3:
                    query = con.createQuery("select r FROM Horario h join h.ruta r join r.pasajeroRutas pr WHERE pr.pasajero.idPasajero= :id and pr.id.dia = 'Miércoles'");
                    break;
                case 4:
                    query = con.createQuery("select r FROM Horario h join h.ruta r join r.pasajeroRutas pr WHERE pr.pasajero.idPasajero= :id and pr.id.dia = 'Jueves'");
                    break;                    
                case 5: 
                    query = con.createQuery("select r FROM Horario h join h.ruta r join r.pasajeroRutas pr WHERE pr.pasajero.idPasajero= :id and pr.id.dia = 'Viernes'");
                    break;
                case 6:
                    query = con.createQuery("select r FROM Horario h join h.ruta r join r.pasajeroRutas pr WHERE pr.pasajero.idPasajero= :id and pr.id.dia = 'Sábado'");
                    break;
                default:
                    break;
            }
            if (query != null) {
                query.setParameter("id", id);
            } else {
                System.out.println("El query resultó shafa");
            }
        } catch(Exception e) {
            System.err.println("Hubo un error obteniendo la ruta para el dia "+dia);
            e.printStackTrace();
        }
        if(query.list().isEmpty()){
            System.out.println("No hubo ruta para el dia "+dia);
            return null;
        }
        return (Ruta) query.list().get(0);
    }

    /*
    public Horario getHorario(int id) {
        Horario horario = new Horario();
        try {
        if (con == null || !con.isOpen()) {
            con = ConexionBD.getSessionFactory().openSession();
        }
        Query query = con.createQuery("select h from Horario h join h.ruta r join r.pasajeroRutas pr where pr.pasajero.idPasajero = :id");
        query.setParameter("id", id);
        if(!query.list().isEmpty())
            horario = (Horario) query.list().get(0);
        } catch(Exception e) {
            System.err.println("Error obteniendo horario para el pasajero "+id);
            e.printStackTrace();
        } finally {
            con.close();
            return horario;
        }
        
    }*/
    
    public List<CalificacionPasajero> listarCalificaciones(int idP) {
        Criteria c;
        List<CalificacionPasajero> lstCalif = null;
        try {
            if (con == null || !con.isOpen()) {
                con = ConexionBD.getSessionFactory().openSession();
            }
            Query query = con.createQuery("select c from CalificacionPasajero c where c.pasajero.idPasajero = :id");
            query.setParameter("id", idP);
            lstCalif = query.list();
            if (lstCalif.isEmpty()) {
                System.out.println("Está vacía para el id " + idP);
            } else {
                System.out.println(lstCalif.get(0).getDescripcion());
            }
            System.out.println("Se ha creado la lista de calificaciones");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //con.close();
            return lstCalif;
        }
    }
}
