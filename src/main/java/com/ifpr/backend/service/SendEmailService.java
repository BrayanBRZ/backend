package com.ifpr.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendEmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void sendEmail(String recipient, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }

    public void sendEmailTemplate(
            String recipient,
            String subject,
            String template,
            Context context) {
        String templateString = templateEngine.process(template, context);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
            helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(templateString, true);
            javaMailSender.send(message);
        } catch (MessagingException exception) {
            throw new MailPreparationException("Não foi possível preparar o e-mail com template", exception);
        }
    }

}
