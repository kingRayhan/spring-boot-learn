package dev.rayhan.spring_store.services;

import dev.rayhan.spring_store.entities.Address;
import dev.rayhan.spring_store.entities.Profile;
import dev.rayhan.spring_store.entities.User;
import dev.rayhan.spring_store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    public void createUserWithRelatedData() {
        var user = User
                .builder()
                .name("Rayhan")
                .email("<EMAIL>")
                .password("<PASSWORD>")
                .build();
        var address = Address.builder()
                .street("123 Main St")
                .city("Springfield")
                .zip("12345")
                .build();
        var profile = Profile.builder()
                .bio("I'm a developer")
                .loyaltyPoints(1000)
                .phoneNumber("phone1")
                .dob(LocalDate.now())
                .build();

        user.addAddress(address);
        user.setProfile(profile);
        userRepository.save(user);
    }
}
