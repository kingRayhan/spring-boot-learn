package dev.rayhan.spring_store.apis.product.dtos;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.util.UUID;

@Data
public class UpdateProductPayload {
  private String name;
  private String description;

  @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
  private Double price;

  private UUID categoryId;
}
