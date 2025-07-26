package dev.rayhan.spring_store.apis.product.entities;


import dev.rayhan.spring_store.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "categories")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "category")
    @Builder.Default
    @ToString.Exclude
    Set<Product> products = new HashSet<>();
}
