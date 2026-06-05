package com.library.app.notification.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPasswordResetEmail(String toEmail, String resetLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@myapp.com");
        message.setTo(toEmail);
        message.setSubject("Reset Your Password");
        message.setText("You requested a password reset. Click the link below to reset your password(expires in 5 minutes):\n" + resetLink + "\n\nIf you did not request a password reset, please ignore this email.");

        mailSender.send(message);
    }
}
