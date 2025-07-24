package dev.rayhan.spring_store.mappers;

import dev.rayhan.spring_store.dtos.ProductListDto;
import dev.rayhan.spring_store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductListDtoMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductListDto toDto(Product product);
}
