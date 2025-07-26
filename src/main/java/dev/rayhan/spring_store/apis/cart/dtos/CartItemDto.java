package dev.rayhan.spring_store.apis.cart.dtos;

import lombok.Data;

@Data
public class CartItemDto {
  private CartProductDto product;
  private Integer quantity;
  private Double totalPrice;
}
