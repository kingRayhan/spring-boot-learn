package dev.rayhan.spring_store.entities;

import jakarta.persistence.*;
import lombok.*;

@Setter @Getter @ToString @Builder @AllArgsConstructor @NoArgsConstructor

@Entity
@Data
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private String zip;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
