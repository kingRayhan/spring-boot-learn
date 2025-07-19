package dev.rayhan.ecom.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    NotificationService notificationService;

    public NotificationManager(
            @Qualifier("email")
            NotificationService notificationService
    ) {
        this.notificationService = notificationService;
    }

    public void sendNotification(String message, String toEmail){
        notificationService.send(message, toEmail);
    }
}
