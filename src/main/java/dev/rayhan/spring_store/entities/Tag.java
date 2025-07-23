package dev.rayhan.spring_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter @Setter @Builder
@AllArgsConstructor @NoArgsConstructor

@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "tags")
    @ToString.Exclude
    @Builder.Default
    private Set<User> users = new HashSet<>();
}
