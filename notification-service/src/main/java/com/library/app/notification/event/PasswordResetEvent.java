package com.library.app.notification.event;

import lombok.Data;

@Data
public class PasswordResetEvent {
    private String email;
    private String resetLink;
}
