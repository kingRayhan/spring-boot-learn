package dev.rayhan.ecom.user;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryUserRepository implements UserRepository{
    private final Map<String, User> users = new HashMap<>();

    @Override
    public void save(User user) {
        System.out.println(
                "Saving user with email: " + user.getEmail()
        );
        users.put(user.getEmail(), user);
    }

    @Override
    public User findByEmail(String email) {
        return users.getOrDefault(email, null);
    }
}
