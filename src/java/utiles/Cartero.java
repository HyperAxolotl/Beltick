package utiles;
// File Name SendEmail.java

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import javax.faces.context.FacesContext;
import modelo.PerfilChofer;
import modelo.PerfilPasajero;

public class Cartero {

    private String destinatario;
    private final String remitente = "hyperaxolotl@gmail.com";
    private final String contrasenia = "beltick.pancho666";
    private final String huesped = "localhost";
    private final String tipo;
    private final String idPerfil;
    private final String clave;

    public Cartero(PerfilPasajero pp) {
        destinatario = pp.getPasajero().getPcorreo();
        tipo = "false";
        idPerfil = Integer.toString(pp.getIdPpasajero());
        clave = pp.getClave();
    }

    public Cartero(PerfilChofer pc) {
        destinatario = pc.getChofer().getCcorreo();
        tipo = "true";
        idPerfil = Integer.toString(pc.getIdPchofer());
        clave = pc.getClave();
    }

    public void entrega() {
        /*Map<String, String> params = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        params.put("t", tipo);
        params.put("idU", idPerfil);
        params.put("key", clave);*/
        // Get system properties
        //Properties propiedades = System.getProperties();

        Properties propiedades = new Properties();
        propiedades.put("mail.smtp.auth", "true");
        propiedades.put("mail.smtp.starttls.enable", "true");
        propiedades.put("mail.smtp.host", "smtp.gmail.com");
        propiedades.put("mail.smtp.port", "587");

        Session sesion;
        sesion = Session.getInstance(propiedades,
                new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, contrasenia);
            }
        });
        // Setup mail server
        /*propiedades.setProperty("mail.smtp.host", huesped);
        
        propiedades.setProperty("mail.user", remitente);
        propiedades.setProperty("mail.password", contrasenia);
        // Get the default Session object.
        Session sesion = Session.getDefaultInstance(propiedades);*/
        try {
            // Create a default MimeMessage object.
            MimeMessage mensaje = new MimeMessage(sesion);

            // Set From: header field of the header.
            mensaje.setFrom(new InternetAddress(remitente));

            // Set To: header field of the header.
            mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));

            // Set Subject: header field
            mensaje.setSubject("Beltick: Activación de cuenta");

            // Now set the actual message
            mensaje.setText("Hola, querido usuario.\nPor favor haz click en el siguiente enlace para activar tu cuenta\n\n\t"
                    + "http://localhost:8080/Beltick/Proyecto/ConfirmacionIH.xhtml?t=" + tipo + "&idP=" + idPerfil + "&key=" + clave);
            // Send message
            Transport.send(mensaje);
            System.out.println("Mensaje enviado satisfactoriamente....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
