package com.recodesolutions.itticket.controller;


import com.recodesolutions.itticket.dto.RequestHeaderData;
import com.recodesolutions.itticket.entity.Inward;
import com.recodesolutions.itticket.entity.User;
import com.recodesolutions.itticket.exception.ReITException;
import com.recodesolutions.itticket.models.EmailPayload;
import com.recodesolutions.itticket.repository.InwardRepository;
import com.recodesolutions.itticket.repository.UserRepository;
import com.recodesolutions.itticket.service.EmailService;
import com.recodesolutions.itticket.service.InwardService;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.apache.commons.net.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/")
@RequiredArgsConstructor
@Transactional
public class Controller {

    private final UserRepository userTableRepository;

    private final InwardService inwardService;
    private final InwardRepository inwardRepository;
    private final EmailService emailService;

    @Resource(name = "requestScopeHeaderData")
    private RequestHeaderData requestHeaderData;

    @Value("${html-template.email.inward.creation.location}")
    private String emailTemplateLocation;

    @Value("${html-template.email.inward.creation.variable}")
    private String emailTemplateReferenceVariable;

    @GetMapping("user/mydetails")
    public User receiverDetails(){
        return userTableRepository.findById(requestHeaderData.getId()).get();
    }

    @GetMapping("test/all")
    public List<User> getALlUser() {
        System.out.println(requestHeaderData.getId());
        return userTableRepository.findAll();
    }

    @GetMapping("test/mail/{id}")
    public void  mailSend(@PathVariable Long id) {
//        System.out.println(requestHeaderData.getId());

        Inward inward = inwardRepository.findById(id).orElseThrow(() -> {
            throw new ReITException("50007", "Inward not found");
        });
        sendEmail(inward,"New Creation");
    }

    private void sendEmail(Inward inward, String subject){

        inward.setStringIssuedDate(inward.getIssuedDate().toLocalDate().toString());
        inward.setProviderSignature(Base64.encodeBase64String(inward.getSignature().getProviderSignature()));
        inward.setReceiverSignature(Base64.encodeBase64String(inward.getSignature().getReceiverSignature()));
        String[] sendTo = {"arvinth.padmanabhan@recodesolutions.com"};
        EmailPayload emailPayload = EmailPayload.builder()
                .token(requestHeaderData.getToken())
                .sendFrom(requestHeaderData.getEmail())
                .sendTo(sendTo)
                .subject(subject + inward.getDisplayId()).data(inward)
                .templateLocation(emailTemplateLocation).templateReference(emailTemplateReferenceVariable).build();
        emailService.sendMail(emailPayload);
    }
}
