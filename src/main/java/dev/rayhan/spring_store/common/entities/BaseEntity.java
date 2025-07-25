package dev.rayhan.spring_store.common.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;
}