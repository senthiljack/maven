package com.recodesolutions.itticket.service;

import com.recodesolutions.itticket.models.EmailPayload;

public interface EmailService {
    void sendMail(EmailPayload emailPayload);
}
