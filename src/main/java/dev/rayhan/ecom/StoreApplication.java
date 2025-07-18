package dev.rayhan.ecom;

import dev.rayhan.ecom.notification.NotificationManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        var app = SpringApplication.run(StoreApplication.class, args);
//        var nm = app.getBean(NotificationManager.class);
//        nm.sendNotification("Hello World");

        var os = app.getBean(OrderService.class);

        os.placeOrder();
    }
}
