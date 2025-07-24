package dev.rayhan.spring_store.repositories;

import dev.rayhan.spring_store.dtos.UserDto;
import dev.rayhan.spring_store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<UserDto> findUsersByNameIgnoreCase(String name);
}
