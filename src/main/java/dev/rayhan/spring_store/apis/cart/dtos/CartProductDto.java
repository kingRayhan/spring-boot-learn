package dev.rayhan.spring_store.apis.cart.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class CartProductDto {
  private UUID id;
  private String name;
  private Double price;
}
