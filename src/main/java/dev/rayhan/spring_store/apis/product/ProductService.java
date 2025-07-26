package dev.rayhan.spring_store.apis.product;

import dev.rayhan.spring_store.apis.product.dtos.CreateProductPayload;
import dev.rayhan.spring_store.apis.product.dtos.ProductListFilterRequestQueryParam;
import dev.rayhan.spring_store.apis.product.dtos.UpdateProductPayload;
import dev.rayhan.spring_store.apis.product.mappers.ProductMapper;
import dev.rayhan.spring_store.apis.product.repositories.CategoryRepository;
import dev.rayhan.spring_store.apis.product.repositories.ProductRepository;
import dev.rayhan.spring_store.common.PaginationHelper;
import dev.rayhan.spring_store.apis.product.dtos.ProductListDto;
import dev.rayhan.spring_store.apis.product.entities.Category;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper mapper;
  private final ProductMapper productMapper;
  private final CategoryRepository categoryRepository;

  ResponseEntity<List<ProductListDto>> getAllProducts(ProductListFilterRequestQueryParam filter) {
    if (filter.getCategoryId() != null) {

      var category = Category.builder().id(filter.getCategoryId()).build();
      return ResponseEntity.ok(
        productRepository.findAllByCategory(category, PaginationHelper.createPageable(
            filter.getPage(),
            filter.getLimit(),
            filter.getSort(),
            filter.getSortBy().toString()
          )).stream()
          .map(mapper::entityToProductListDto).toList()
      );
    }

    return ResponseEntity.ok(
      productRepository.findAll(
        PaginationHelper.createPageable(
          filter.getPage(),
          filter.getLimit(),
          filter.getSort(),
          filter.getSortBy().toString()
        )
      ).stream().map(productMapper::entityToProductListDto).toList()
    );
  }

  public ProductListDto createProduct(CreateProductPayload payload) {
    var category = categoryRepository
      .findById(payload.getCategoryId())
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

    var product = productMapper.createProductPayloadToEntity(payload);
    product.setCategory(category);
    var createdProduct = productRepository.save(product);
    return mapper.entityToProductListDto(createdProduct);
  }


  public ProductListDto updateProduct(UUID id, UpdateProductPayload payload) {
    // TODO: sync using map struct
    var product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    mapper.syncUpdateProductPayloadWithEntity(payload, product);

//    if (payload.getName() != null) {
//      product.setName(payload.getName());
//    }
//
//    if (payload.getPrice() != null){
//      product.setPrice(payload.getPrice());
//    }
//
//    if (payload.getCategoryId() != null) {
//      var category = categoryRepository.findById(payload.getCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
//      product.setCategory(category);
//    }


    var savedProduct = productRepository.save(product);
    return mapper.entityToProductListDto(savedProduct);
  }

  public void deleteProduct(UUID id) {
    var product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    productRepository.delete(product);
  }
}
