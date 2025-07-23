package dev.rayhan.spring_store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Data
@Table(name = "addresses")
public class Address extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "zip")
    private String zip;

    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;
}
