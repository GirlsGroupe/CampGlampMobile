/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.utils;

//*


/**
 *
 * @author MossMoss
 */


import com.codename1.ui.util.Resources;
import com.mycompany.gui.ConfirmerCode;
import com.mycompany.gui.SignInForm;
import com.sun.mail.smtp.SMTPTransport;
import java.util.Date;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendEmail {


    public SendEmail() {

    }


    /*public static void SendEmailMdp(String email)  {

          
            java.util.Properties props = new java.util.Properties();
            props.put("mail.transport.protocol", "smtp"); //SMTP protocol
		props.put("mail.smtps.host", "smtp.gmail.com"); //SMTP Host
		props.put("mail.smtps.auth", "true"); //enable authentication
		      props.put("mail.smtp.starttls.enable", "true");

            try{
            Session session = Session.getInstance(props, null);
            
            MimeMessage msg = new MimeMessage(session);
           
            msg.setFrom(new InternetAddress("Reintialisation mor de passe <monEmail@domaine.com>"));
            
            msg.setRecipients(Message.RecipientType.TO, email);
            
            msg.setSubject("Email de réinitialisation du mot de passe");
            msg.setSentDate(new Date(System.currentTimeMillis()));
            
            msg.setText("Salut " +  ",\nBesoin de réinitialiser votre mot de passe?\n\nUtilisez votre code secret!\n" + CodeGenerator.genererCode() + "\n\nSi vous n'avez pas oublié votre mot de passe, vous pouvez ignorer cet e-mail.");
            SMTPTransport st=(SMTPTransport)session.getTransport("smtps");
            System.out.println("mazelt mabaathtech1");
            st.connect("smtp.gmail.com",465,"campglamp0@gmail.com","50245288");
            System.out.println("mazelt mabaathtech2");
            st.sendMessage(msg, msg.getAllRecipients());
            System.out.println("hani baatht melghadi");
            System.out.println("server reponse : "+st.getLastServerResponse());
        }catch(Exception e){
            e.printStackTrace();
        }
          
    }
    */

  public static int SendEmailMdp(String email) {

        SendEmail se = new SendEmail();
        //authentication info
     
            final String username = "campglamp0@gmail.com";
            final String password = "50245288";
            java.util.Properties props = new java.util.Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.ssl.enable", true);

            Session session = Session.getInstance(props, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });               
           
            //Start our mail message
            MimeMessage msg = new MimeMessage(session);
            try {
                msg.setFrom(new InternetAddress(username));

                msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
                msg.setSubject("Email de réinitialisation du mot de passe");
                msg.setText("Salut " +  ",\nBesoin de réinitialiser votre mot de passe?\n\nUtilisez votre code secret!\n" + CodeGenerator.genererCode() + "\n\nSi vous n'avez pas oublié votre mot de passe, vous pouvez ignorer cet e-mail.");
                Transport.send(msg);
                System.out.println("Email envoyé");

                return 1;
            } catch (MessagingException e) {
                System.out.println(e.getMessage());
                            e.printStackTrace();

            }
        return 0;
    }

}
