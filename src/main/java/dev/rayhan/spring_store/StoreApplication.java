package dev.rayhan.spring_store;

import dev.rayhan.spring_store.entities.Profile;
import dev.rayhan.spring_store.entities.Tag;
import dev.rayhan.spring_store.entities.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
//        SpringApplication.run(StoreApplication.class, args);
        var user = User.builder()
                .name("Rayhan")
                .email("<EMAIL>")
                .password("<PASSWORD>")
                .build();

        var profile = Profile.builder().bio("Hello World").build();

        user.setProfile(profile);
        System.out.println(user);
    }
}