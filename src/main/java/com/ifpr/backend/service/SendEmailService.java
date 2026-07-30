package com.ifpr.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendEmail(String recipient, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

}
