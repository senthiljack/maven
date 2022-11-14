package com.recodesolutions.itticket.service.impl;

import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.helper.MailTokenGenerator;
import com.recodesolutions.itticket.models.EmailPayload;
import com.recodesolutions.itticket.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.annotation.Resource;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    private final MailTokenGenerator mailTokenGenerator;
    private final TemplateEngine templateEngine;

    @Async
    public void sendMail(EmailPayload emailPayload) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.auth.mechanisms", "XOAUTH2");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.debug.auth", "false");
            props.put("mail.debug", "false");
            Session session = Session.getDefaultInstance(props);

            Transport transport = session.getTransport("smtp");
            transport.connect(emailPayload.getSendFrom(), mailTokenGenerator.getAuthorizationTokenAsync(emailPayload.getToken()));

            Context context = new Context();
            context.setVariable(emailPayload.getTemplateReference(), emailPayload.getData());

            String process = templateEngine.process(emailPayload.getTemplateLocation(), context);

            var toAddress = new InternetAddress(emailPayload.getSendTo()[0]);
            var fromAddress = new InternetAddress(emailPayload.getSendFrom());

            MimeMessage message = new MimeMessage(session);
            message.setFrom(fromAddress);
            message.addRecipient(MimeMessage.RecipientType.TO,
                    toAddress);

            message.setSubject(emailPayload.getSubject());
            message.setText(process, "UTF-8", "html");
            transport.sendMessage(message, message.getAllRecipients());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
