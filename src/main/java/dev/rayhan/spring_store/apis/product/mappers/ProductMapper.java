package dev.rayhan.spring_store.apis.product.mappers;

import dev.rayhan.spring_store.apis.product.dtos.CreateProductPayload;
import dev.rayhan.spring_store.apis.product.dtos.UpdateProductPayload;
import dev.rayhan.spring_store.apis.product.entities.Product;
import dev.rayhan.spring_store.common.dtos.ProductListDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(target = "categoryId", source = "category.id")
  ProductListDto entityToProductListDto(Product product);

  Product createProductPayloadToEntity(CreateProductPayload createProductPayload);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void syncUpdateProductPayloadWithEntity(UpdateProductPayload payload, @MappingTarget Product product);
}
