package com.DecoMood.service;

import com.DecoMood.config.WebConfig;
import com.DecoMood.entity.Usuario;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

@Component
public class EmailService {

        @Value("${email.empresa}")
        private String EMAIL_EMPRESA;
        @Value("${email.usuario}")
        private String remitente;
        @Value("${email.password}")
        private String contraseña;
        @Autowired
        private WebConfig webConfig;

        public  void enviarEmail(Usuario usuario){
            try {
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
                montarEmailCliente(usuario, session);
                montarEmailEmpresa(usuario, session);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        private void montarEmailEmpresa(Usuario usuario, Session session) throws Exception {

            MimeMessage mensaje = new MimeMessage(session);

            mensaje.setFrom(new InternetAddress(remitente));

            mensaje.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(EMAIL_EMPRESA));

            mensaje.setSubject("Solicitud de presupuesto - " + usuario.getNombre());

            // Ruta física de la imagen
            String rutaImagen = webConfig.getUploadDir() + "/" + usuario.getStock().getImagen();

            // HTML del email
            String html = """
                <!DOCTYPE html>
                <html lang="es">
                <body style="font-family:Arial;background:#f5f5f5;padding:30px;">
    
                <div style="max-width:700px;margin:auto;background:white;
                            border-radius:12px;overflow:hidden;
                            box-shadow:0 2px 10px rgba(0,0,0,.15);">
    
                    <div style="background:#4F6F52;color:white;padding:25px;text-align:center;">
                        <h1>Nueva solicitud de presupuesto</h1>
                        <p>DecoMood </p>
                    </div>
    
                    <div style="padding:30px;">
    
                        <h2 style="color:#4F6F52;">Datos del cliente</h2>
    
                        <table style="width:100%%;border-collapse:collapse;">
    
                            <tr>
                                <td style="font-weight:bold;padding:8px;">Nombre</td>
                                <td>%s</td>
                            </tr>
    
                            <tr style="background:#f5f5f5;">
                                <td style="font-weight:bold;padding:8px;">Email</td>
                                <td>%s</td>
                            </tr>
                            <tr style="background:#f5f5f5;">
                                <td style="font-weight:bold;padding:8px;">Telefono</td>
                                <td>%s</td>
                            </tr>
    
                            <tr>
                                <td style="font-weight:bold;padding:8px;">Mueble</td>
                                <td>%s</td>
                            </tr>
    
                        </table>
    
                        <br>
    
                        <h2 style="color:#4F6F52;">Descripción</h2>
    
                        <div style="background:#f5f5f5;
                                    padding:15px;
                                    border-left:4px solid #4F6F52;">
    
                            %s
    
                        </div>
    
                        <br>
    
                        <h2 style="color:#4F6F52;">Imagen del mueble</h2>
    
                        <div style="text-align:center;">
                            <img src="cid:mueble"
                                 style="width:420px;
                                        border-radius:8px;
                                        border:1px solid #ddd;">
                        </div>
    
                    </div>
    
                </div>
    
                </body>
                </html>
                """.formatted(
                    usuario.getNombre(),
                    usuario.getEmail(),
                    usuario.getTelefono(),
                    usuario.getStock().getNombre(),
                    usuario.getDescripcion()
            );

            // Cuerpo HTML
            MimeBodyPart cuerpo = new MimeBodyPart();
            cuerpo.setContent(html, "text/html; charset=UTF-8");
            // Imagen integrada
            MimeBodyPart imagen = new MimeBodyPart();
            imagen.attachFile(new File(rutaImagen));
            imagen.setDisposition(MimeBodyPart.INLINE);
            imagen.setContentID("<mueble>");
            // Unimos ambas partes
            MimeMultipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(cuerpo);
            multipart.addBodyPart(imagen);
            mensaje.setContent(multipart);
            Transport.send(mensaje);
        }
        /************* Email para el cliente *************/
        private void montarEmailCliente(Usuario usuario, Session session)
                throws MessagingException, IOException {

            MimeMessage mensaje = new MimeMessage(session);
            mensaje.setFrom(new InternetAddress(remitente,"Decomood"));
            // Se envía al cliente
            mensaje.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(usuario.getEmail()));
            mensaje.setSubject("Hemos recibido tu solicitud de presupuesto | DecoMood");
            // Ruta física de la imagen
            String rutaImagen = webConfig.getUploadDir() + "/" + usuario.getStock().getImagen();
            String html = """
                <!DOCTYPE html>
                <html lang="es">
                <body style="margin:0;padding:30px;background:#F4F2EE;font-family:Arial,Helvetica,sans-serif;color:#333;">
    
                <div style="max-width:700px;
                            margin:auto;
                            background:white;
                            border-radius:12px;
                            overflow:hidden;
                            border:1px solid #ddd;">
    
                    <!-- Cabecera -->
    
                    <div style="background:#4F6F52;
                                color:white;
                                padding:35px;
                                text-align:center;">
    
                        <h1 style="margin:0;">
                            DecoMood ♻️
                        </h1>
    
                        <p style="margin-top:10px;">
                            Gracias por confiar en nosotros
                        </p>
    
                    </div>
    
                    <!-- Contenido -->
    
                    <div style="padding:35px;">
    
                        <h2 style="color:#4F6F52;">
                            Hola %s,
                        </h2>
    
                        <p>
                            Hemos recibido correctamente tu solicitud de presupuesto.
                        </p>
    
                        <p>
                            Queremos agradecerte la confianza depositada en
                            <strong>DecoMood</strong>.
                            Cada uno de nuestros muebles está elaborado de forma
                            completamente artesanal utilizando materiales sostenibles
                            y recuperados siempre que es posible.
                        </p>
    
                        <p>
                            Nuestro equipo revisará tu solicitud y se pondrá en
                            contacto contigo en un plazo aproximado de
                            <strong>24 - 48 horas.</strong>
                        </p>
    
                        <hr style="margin:30px 0;">
    
                        <h2 style="color:#4F6F52;">
                            Resumen de tu solicitud
                        </h2>
    
                        <table style="width:100%%;
                                      border-collapse:collapse;">
    
                            <tr>
    
                                <td style="padding:10px;
                                           font-weight:bold;
                                           width:180px;
                                           background:#f5f5f5;">
                                    Nombre
                                </td>
    
                                <td style="padding:10px;">
                                    %s
                                </td>
    
                            </tr>
    
                            <tr>
    
                                <td style="padding:10px;
                                           font-weight:bold;
                                           background:#f5f5f5;">
                                    Email
                                </td>
    
                                <td style="padding:10px;">
                                    %s
                                </td>
    
                            </tr>
    
                            <tr>
    
                                <td style="padding:10px;
                                           font-weight:bold;
                                           background:#f5f5f5;">
                                    Mueble
                                </td>
    
                                <td style="padding:10px;">
                                    %s
                                </td>
    
                            </tr>
    
                        </table>
    
                        <br>
    
                        <h2 style="color:#4F6F52;">
                            Tu descripción
                        </h2>
    
                        <div style="
                            background:#F9F9F9;
                            padding:20px;
                            border-left:4px solid #4F6F52;
                            border-radius:6px;
                            line-height:1.6;">
    
                            %s
    
                        </div>
    
                        <br>
    
                        <div style="text-align:center;">
    
                            <h2 style="color:#4F6F52;">
                                Mueble seleccionado
                            </h2>
    
                            <img src="cid:mueble"
                                 style="width:420px;
                                        border-radius:10px;
                                        border:1px solid #ddd;">
    
                        </div>
    
                        <br><br>
    
                        <p>
                            Mientras tanto, si deseas añadir más información o realizar
                            cualquier modificación, puedes responder directamente a este
                            correo. O bien en la opción de contactenos.
                        </p>
    
                        <p>
                            Muchas gracias por apoyar la artesanía y el mobiliario
                            sostenible.
                        </p>
    
                        <br>
    
                        <p>
    
                            Un cordial saludo.
    
                        </p>
    
                        <strong>
                            Equipo DecoMood 💚
                        </strong>
    
                    </div>
    
                </div>
    
                </body>
                </html>
                """.formatted(
                    usuario.getNombre(),           // Hola %s
                    usuario.getNombre(),           // Nombre
                    usuario.getEmail(),            // Email
                    usuario.getStock().getNombre(),// Mueble
                    usuario.getDescripcion()       // Descripción
            );

            // Cuerpo HTML
            MimeBodyPart cuerpo = new MimeBodyPart();
            cuerpo.setContent(html, "text/html; charset=UTF-8");
            // Imagen integrada
            MimeBodyPart imagen = new MimeBodyPart();
            imagen.attachFile(new File(rutaImagen));
            imagen.setDisposition(MimeBodyPart.INLINE);
            imagen.setContentID("<mueble>");
            // Unimos ambas partes
            MimeMultipart multipart = new MimeMultipart("related");
            multipart.addBodyPart(cuerpo);
            multipart.addBodyPart(imagen);
            mensaje.setContent(multipart);
            Transport.send(mensaje);
        }
}
