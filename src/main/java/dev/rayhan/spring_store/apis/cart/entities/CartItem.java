package dev.rayhan.spring_store.apis.cart.entities;

import dev.rayhan.spring_store.apis.product.entities.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor

@Entity
@Table(name = "cart_items")
public class CartItem {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "cart_id")
  private Cart cart;


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "quantity")
  private Integer quantity;

  @Column(name = "created_at")
  private Instant createdAt;

  @Column(name = "updated_at")
  private Instant updatedAt;

  public Double getTotalPrice() {
    return product.getPrice() * quantity;
  }
}