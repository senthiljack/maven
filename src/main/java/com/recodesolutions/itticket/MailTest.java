package com.recodesolutions.itticket;


import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailTest {
    public static void main(String[] args) {
        try{
            Properties props = new Properties();
//         properties.put("mail.smtp.auth.mechanisms", "XOAUTH2");
//         properties.put("mail.smtp.starttls.enable", "true");
//         properties.put("mail.smtp.starttls.required", "true");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.host", "smtp.office365.com");
         props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug.auth", "true");
            props.put("mail.debug", "true");
            Session session = Session.getDefaultInstance(props);

            String token= "eyJ0eXAiOiJKV1QiLCJub25jZSI6Im9lOF9jRW85NHBwa05VQmc3RW9iRHZCYkZxVkZsT1NsVmEtc1Nhb3h6LXciLCJhbGciOiJSUzI1NiIsIng1dCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSIsImtpZCI6IjJaUXBKM1VwYmpBWVhZR2FYRUpsOGxWMFRPSSJ9.eyJhdWQiOiJodHRwczovL291dGxvb2sub2ZmaWNlLmNvbSIsImlzcyI6Imh0dHBzOi8vc3RzLndpbmRvd3MubmV0LzA5ZDhjMmI4LTg1MGMtNGQ5Ni1hNDg2LWNiYzhkZmU4NjYxMC8iLCJpYXQiOjE2NjQwNDIzNjAsIm5iZiI6MTY2NDA0MjM2MCwiZXhwIjoxNjY0MDQzNDc5LCJhY2N0IjowLCJhY3IiOiIxIiwiYWlvIjoiQVZRQXEvOFRBQUFBOHEwdzJ2cnBSbzB1Nis2WlpUREtaWlB3WUNKajkrNFdrUlBOVTdCUGtiTFYyM3NXWjFFbmtZT0k5akxJRER1SEtsOFE2b2h1RGswY3g5UUFRcDdzc1FlRDgxWkxPdXJCV1VwdUV0VXpyOXM9IiwiYW1yIjpbInB3ZCIsIm1mYSJdLCJhcHBfZGlzcGxheW5hbWUiOiJSZVBhc3MiLCJhcHBpZCI6IjY2MzA4NzNkLWViMDktNDQwYS05OGEzLTc5MTg0ZTdlZGU1NyIsImFwcGlkYWNyIjoiMSIsImVuZnBvbGlkcyI6W10sImZhbWlseV9uYW1lIjoiS2FubmFuIFBhZG1hbmFiaGFuIiwiZ2l2ZW5fbmFtZSI6IkFydmludGgiLCJpcGFkZHIiOiIyNy41LjU4LjIyNCIsIm5hbWUiOiJBcnZpbnRoIEthbm5hbiBQYWRtYW5hYmhhbiIsIm9pZCI6IjUzNzQzZThiLWM5M2MtNGY5ZC1iZjNjLTY0OWQ0NGMwZjZiYyIsInB1aWQiOiIxMDAzMjAwMTlFRUMxOUFCIiwicmgiOiIwLkFWTUF1TUxZQ1F5RmxrMmtoc3ZJMy1obUVBSUFBQUFBQVBFUHpnQUFBQUFBQUFCVEFBSS4iLCJzY3AiOiJNYWlsLlNlbmQgU01UUC5TZW5kIFVzZXIuUmVhZCIsInNpZCI6IjlmY2VjNWEwLWM1MjgtNDZlNS04ZWY3LTFjZDdlYTU0MDI4MSIsInN1YiI6IlJEVm8ycjdxdWFuTUtVbi1xekdrWFRqSmZEM1JZR0NRSkx3RDZqNVN3WDQiLCJ0aWQiOiIwOWQ4YzJiOC04NTBjLTRkOTYtYTQ4Ni1jYmM4ZGZlODY2MTAiLCJ1bmlxdWVfbmFtZSI6ImFydmludGgucGFkbWFuYWJoYW5AcmVjb2Rlc29sdXRpb25zLmNvbSIsInVwbiI6ImFydmludGgucGFkbWFuYWJoYW5AcmVjb2Rlc29sdXRpb25zLmNvbSIsInV0aSI6IkNnV1NwQnpqU2t1ZVppRzZMcEJMQUEiLCJ2ZXIiOiIxLjAiLCJ3aWRzIjpbImI3OWZiZjRkLTNlZjktNDY4OS04MTQzLTc2YjE5NGU4NTUwOSJdfQ.egqsM2BwxTKGGsOT4M1_Rk8JOuhqpai9goEntxkMjYcOGl0QdUUsnnJpbXHuk9jGcWD4GOwKl2zMyNgYZugDM-YRqK_4q61sfVVm0bEjTtRNi9SZvUYF6jwOWcAeWMMAENc921ZOOoBZ6lmqjmelI5TwwYnzWsJQUTkTQ4t0trQHtF-FRuSvWs_mDohJZMbBbNfnXPB8qDrveK8wIp6ILBX3I4xgtEszibByrHUjxzcMrlr331czW6xOG1EQ11VWsi8VluUdIRSbsce2K_BLlcyWh-i_s5ienUpobudqJJiWiY-zs10oZxYAd_swV1I-QIJpJ1dmPN6Mh12IoecQZg";
                    Transport transport = session.getTransport("smtp");
            transport.connect("arvinth.padmanabhan@recodesolutions.com", token);

            String html = "<!doctype html>\n" +
                    "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\"\n" +
                    "      xmlns:th=\"http://www.thymeleaf.org\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta name=\"viewport\"\n" +
                    "          content=\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n" +
                    "    <title>Email</title>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "<div>Welcome <b>" + "arvinth" + "</b></div>\n" +
                    "\n" +
                    "<div> Your username is <b>" + "arvinth" + "</b></div>\n" +
                    "</body>\n" +
                    "</html>\n";

            Message simpleMessage = new MimeMessage(session);
//         var fromAddress = new InternetAddress("KamerAIDemo@kamerai.ai");
            var toAddress = new InternetAddress("arvinth.padmanabhan@recodesolutions.com");
            var fromAddress = new InternetAddress("arvinth.padmanabhan@recodesolutions.com");
//        var toAddress = new InternetAddress("rajesh.janarthanan@recodesolutions.com");
            simpleMessage.setFrom(fromAddress);
            simpleMessage.setRecipient(Message.RecipientType.TO, toAddress);
            simpleMessage.setSubject("Test Via Java");
//         simpleMessage.setText(html,"UTF-8","html");
//         transport.sendMessage(simpleMessage, simpleMessage.getAllRecipients());


            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(fromAddress);
            // Set To: header field of the header.
            message.addRecipient(MimeMessage.RecipientType.TO,
                    toAddress);//as we are importing the "javax.mail.Message.RecipientType"
            //we have to not set the type as this message.addRecipient(Message.RecipientType.TO,
            //new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("This is the Subject Line!");
            // Now set the actual message
            message.setText(html,"UTF-8","html");
            transport.sendMessage(message, message.getAllRecipients());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
