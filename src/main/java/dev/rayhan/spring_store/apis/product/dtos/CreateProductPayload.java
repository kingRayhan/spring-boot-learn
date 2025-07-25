package dev.rayhan.spring_store.apis.product.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CreateProductPayload {
  @NotBlank(message = "Name is required")
  private String name;

  private String description;

  @NotNull(message = "Price is required")
  @DecimalMin(value = "0.01", message = "Price must be at least 0.01")
  private double price;
  private UUID categoryId;
}
