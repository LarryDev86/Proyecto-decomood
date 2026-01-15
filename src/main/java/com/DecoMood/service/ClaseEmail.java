package com.DecoMood.service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class ClaseEmail {

        @Value("${email.empresa}")
        private String EMAIL_EMPRESA;
        @Value("${email.usuario}")
        private String remitente;
        @Value("${email.password}")
        private String contraseña;

        public  void enviarEmail(String nombreUsuario, String destinatario, String mensajeUsuario) throws AddressException, MessagingException{

               Properties props = new Properties();
               props.put("mail.smtp.auth","true");
               props.put("mail.smtp.starttls.enable","true");
               props.put("mail.smtp.starttls.required","true");
               props.put("mail.smtp.ssl.protocols","TLSv1.2");
               props.put("mail.smtp.ssl.trust","smtp.gmail.com");
               props.put("mail.smtp.host","smtp.gmail.com");
               props.put("mail.smtp.port","587");

               Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                       protected PasswordAuthentication getPasswordAuthentication(){
                           return new PasswordAuthentication(remitente , contraseña);
                       }
               });
               //Invocamos el metodo del cliente
               montarEmail(nombreUsuario,session, destinatario, remitente,mensajeCliente(nombreUsuario)); 
               
               //Invocar para el emial del la empresa
               montarEmail(nombreUsuario,session, EMAIL_EMPRESA, remitente,mensajeEmpresa(nombreUsuario,destinatario,mensajeUsuario));
        }
  
        private void montarEmail(String nombreUsuario, Session session, String destinatario, String remitente, String asunto) throws AddressException, MessagingException{
                
                Message mensaje = new MimeMessage(session);
                mensaje.setFrom(new InternetAddress(remitente));
                mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
                if(destinatario.equalsIgnoreCase(EMAIL_EMPRESA)){
                    mensaje.setSubject("Solicitud de informacion - " +nombreUsuario+"\n\n"); 
                }else{
                    String reciclaje = "♻️";
                    mensaje.setSubject("DecoMood CCL  "+reciclaje+"\n\n");
                }
                mensaje.setText(asunto);
                Transport.send(mensaje);
        }
        
        private  String mensajeCliente(String nombre){
            String cara = " 😊";
            String corazon = "💚";
            return  "Hola "+nombre+" "+cara+",\nMuchas gracias por ponerte en contacto con Deco Mood.\n" 
                    +"Queremos que sepas que en cada uno de nuestros muebles depositamos mucho amor y dedicación.\n"
                    + " Cada pieza que creamos es única y está hecha de forma completamente artesanal, "
                    + "utilizando materiales recuperados cuando es posible, para darles una nueva vida Trabajamos principalmente con maderas nobles que han sido abandonadas,"
                    + " combinándolas con hierro forjado, aceites vegetales, ceras naturales y tintas ecológicas para conseguir acabados duraderos, sostenibles y preciosos.\n"
                    +" Fabricamos todo en España (Barcelona), cuidando cada detalle y respetando el medio ambiente.\n"
                    +"En breve nos pondremos en contacto contigo para resolver tu consulta.\n" 
                    +"¡Gracias por confiar en nosotros y por apoyar el trabajo artesano y sostenible! \n"
                    + "Un abrazo,\n\n"
                    + "El equipo de Deco Mood "+corazon+".";
        }
        
        private String mensajeEmpresa(String nombreUsuario, String destinatario, String asunto){      
            return "El usuario "+nombreUsuario+" ha solicitado informacion, \n\nEmail: "+destinatario+"\n\n Asunto: "+asunto;
        }
}
