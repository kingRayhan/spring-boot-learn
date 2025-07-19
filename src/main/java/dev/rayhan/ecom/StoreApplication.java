package dev.rayhan.ecom;

import dev.rayhan.ecom.user.User;
import dev.rayhan.ecom.user.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
//        var userService = context.getBean(UserService.class);
//        var u1 = userService.register(
//                new User(1L, "Rayhan", "rayhan095@gmail.com", "pass1")
//        );
//        System.out.println(u1);
    }
}
