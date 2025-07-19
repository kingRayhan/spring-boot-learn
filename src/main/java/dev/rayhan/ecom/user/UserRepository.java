package dev.rayhan.ecom.user;

public interface UserRepository {
    void save(User user);
    User findByEmail(String email);
}
