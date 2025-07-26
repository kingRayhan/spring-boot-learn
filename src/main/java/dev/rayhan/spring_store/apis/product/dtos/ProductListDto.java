package dev.rayhan.spring_store.apis.product.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class ProductListDto {
    private UUID id;
    private String name;
    private String description;
    private Double price;
    private String categoryId;
}
