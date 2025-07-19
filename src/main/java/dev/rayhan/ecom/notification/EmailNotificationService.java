package dev.rayhan.ecom.notification;

import org.springframework.stereotype.Service;

@Service("email")
public class EmailNotificationService implements NotificationService{
    @Override
    public void send(String message, String toEmail) {
        System.out.println(
                "Sending email notification to: " + toEmail + " with message: " + message
        );
    }
}
