package dev.rayhan.ecom.user;

import dev.rayhan.ecom.notification.NotificationService;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public UserService(UserRepository userRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }


    public User register(User user){
        if(userRepository.findByEmail(user.getEmail()) != null){
            throw new IllegalArgumentException("User already exists");
        }
        userRepository.save(user);
        notificationService.send("Welcome to our store", user.getEmail());
        return user;
    }
}
