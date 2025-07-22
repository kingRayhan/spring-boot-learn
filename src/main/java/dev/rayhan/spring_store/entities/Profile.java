package dev.rayhan.spring_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Data @AllArgsConstructor @NoArgsConstructor @Builder
@Entity
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "bio")
    private String bio;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "date_of_birth")
    private LocalDate dob;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @OneToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
//    @MapsId
    private User user;
}
