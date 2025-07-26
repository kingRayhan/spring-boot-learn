package dev.rayhan.spring_store.apis.cart.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "carts")
public class Cart {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @Column(name = "id", nullable = false)
  private UUID id;

  @OneToMany(mappedBy = "cart", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
  @Builder.Default
  Set<CartItem> items = new HashSet<>();

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  public void addItemToCart(CartItem item) {
    items.add(item);
    item.setCart(this);
  }
}