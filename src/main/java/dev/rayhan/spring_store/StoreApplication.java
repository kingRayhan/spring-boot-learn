package dev.rayhan.spring_store;


import dev.rayhan.spring_store.entities.Address;
import dev.rayhan.spring_store.entities.User;
import dev.rayhan.spring_store.repositories.UserRepository;
import dev.rayhan.spring_store.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);

        var userRepository = context.getBean(UserRepository.class);
        var userService = context.getBean(UserService.class);

//        userService.createUserWithRelatedData();
        userService.deleteRelatedData();
//        userRepository.deleteById(UUID.fromString("2f147f10-8c3f-4d91-bb8a-dcae4c329686"));
    }
}