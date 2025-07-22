package dev.rayhan.spring_store.repositories;

import dev.rayhan.spring_store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> { }
