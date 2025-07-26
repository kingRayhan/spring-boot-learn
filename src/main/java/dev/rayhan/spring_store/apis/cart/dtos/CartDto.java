package dev.rayhan.spring_store.apis.cart.dtos;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class CartDto {
  private UUID id;
  private List<CartItemDto> items = new ArrayList<>();
  private Double totalPrice = 0.0;
}
