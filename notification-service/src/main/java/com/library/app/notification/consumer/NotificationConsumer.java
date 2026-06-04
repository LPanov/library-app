package com.library.app.notification.consumer;

import com.library.app.notification.event.PasswordResetEvent;
import com.library.app.notification.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationConsumer {

    private final EmailService emailService;

    @KafkaListener(topics = "password-reset-events", groupId = "notification-group")
    public void consumePasswordReset(PasswordResetEvent event) {
        // Log it or process it
        emailService.sendPasswordResetEmail(event.getEmail(), event.getResetLink());
    }
}
