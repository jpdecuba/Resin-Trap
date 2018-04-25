package Server.Mail;

import Server.BackEndServer;
import Shared.Mail.MailMsg;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.*;

public class EmailSend {


    final String username = "noreply.achemahoneypot@gmail.com";
    final String password = "achemahoneypot";

    Properties props = new Properties();

    public EmailSend() {
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
    }



    public boolean SendEmail(MailMsg mail){
        StringBuilder sb = new StringBuilder();
        sb.append("Your honeypot got a connection on Service: " + mail.getServices() );
        sb.append("\n");
        sb.append("Attacker IP: " + mail.getIP());
        sb.append("\n");
        sb.append("Attacker Port: " + mail.getPort());


        for(String item : mail.getEmails()) {
            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(username, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress("HoneyPot"));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(item));
                message.setSubject("Honeypot connection ALERT!");
                message.setText(sb.toString());
                Transport.send(message);

            } catch (javax.mail.MessagingException e) {
                return false;
                //throw new RuntimeException(e);

            }

        }

        return true;
    }

    public static void main(String args[]) throws Exception {
        EmailSend t = new EmailSend();

        ArrayList<String> t2 = new ArrayList<>();
        t2.add("dotter5380@gmail.com");

        MailMsg m = new MailMsg("FTP","8.8.8.8",500,t2);
        t.SendEmail(m);
    }


}

