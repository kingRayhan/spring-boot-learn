package dev.rayhan.spring_store.apis.product.entities;


import dev.rayhan.spring_store.common.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder @ToString

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    @Column(name = "price")
    private Double price;

    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "category_id")
    private Category category;
}
