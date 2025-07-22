package dev.rayhan.spring_store;

import dev.rayhan.spring_store.entities.Address;
import dev.rayhan.spring_store.entities.User;
import org.springframework.boot.SpringApplication;
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
        var address1 = Address.builder()
                .street("123 Main St")
                .city("Springfield")
                .zip("12345")
                .build();
        var address2 = Address.builder()
                .street("456 Main St")
                .city("Springfield")
                .zip("12345")
                .build();

        user.addAddress(address1);
        user.addAddress(address2);
        System.out.println(user);
    }
}