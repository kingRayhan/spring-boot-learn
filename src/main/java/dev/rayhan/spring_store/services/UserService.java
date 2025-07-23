package dev.rayhan.spring_store.services;

import dev.rayhan.spring_store.entities.Address;
import dev.rayhan.spring_store.entities.Profile;
import dev.rayhan.spring_store.entities.Tag;
import dev.rayhan.spring_store.entities.User;
import dev.rayhan.spring_store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {


    private final UserRepository userRepository;


    @Transactional
    public void createUserWithRelatedData() {
        var user = User
                .builder()
                .name("Rayhan")
                .email("<EMAIL>")
                .password("<PASSWORD>")
                .build();
//        var address = Address.builder()
//                .street("123 Main St")
//                .city("Springfield")
//                .zip("12345")
//                .build();
//        var profile = Profile.builder()
//                .bio("I'm a developer")
//                .loyaltyPoints(1000)
//                .phoneNumber("phone1")
//                .dob(LocalDate.now())
//                .build();

        var tag1 = Tag.builder().id(UUID.fromString("30f298f3-c794-43ca-b33a-00d62a92d715")).name("tag1").description("tag1 desc").build();
//        var tag2 = Tag.builder().name("tag2").description("tag2 desc").build();
//        var tag3 = Tag.builder().name("tag3").description("tag3 desc").build();

//        user.addAddress(address);
//        user.setProfile(profile);
        user.addTags(List.of(tag1));
        userRepository.save(user);
    }


    @Transactional
    public void deleteRelatedData() {
        var user = userRepository.findById(UUID.fromString("95696d9e-5ff3-472f-a123-de10ed76f475")).orElse(new User());
        user.removeProfile();
        userRepository.save(user);
//        user.getAddresses().forEach(address -> address.setUser(null));

//        userRepository.deleteById(UUID.fromString("092d3647-d92c-48d5-823a-fd7709678c1b"));
    }
}
