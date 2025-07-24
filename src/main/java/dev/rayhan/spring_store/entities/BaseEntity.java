package dev.rayhan.spring_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@MappedSuperclass
public class BaseEntity {
    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    public void initTimeStamp(){
           createdAt = LocalDateTime.now();
           updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAt(){
        updatedAt = LocalDateTime.now();
    }
}