package dev.rayhan.spring_store.apis.product;

import dev.rayhan.spring_store.apis.product.dtos.CreateProductPayload;
import dev.rayhan.spring_store.apis.product.dtos.ProductListFilterRequestQueryParam;
import dev.rayhan.spring_store.apis.product.dtos.UpdateProductPayload;
import dev.rayhan.spring_store.common.dtos.ProductListDto;
import dev.rayhan.spring_store.common.dtos.ProductSortByColumn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
@Tag(name = "Products", description = "Product related operations")
public class ProductController {
  private final ProductService productService;

  @GetMapping("/")
  public ResponseEntity<List<ProductListDto>> index(
    @Valid ProductListFilterRequestQueryParam filter
    ) {
    return productService.getAllProducts(filter);
  }

  @PostMapping("/")
  public ResponseEntity<ProductListDto> store(
    @Valid @RequestBody CreateProductPayload payload,
    UriComponentsBuilder uriBuilder
  ) {
    var product = productService.createProduct(payload);
    var uri = uriBuilder.path("/products/{id}").buildAndExpand(product.getId()).toUri();
    return ResponseEntity.created(uri).body(product);
  }

  @PatchMapping("/{id}")
  public ResponseEntity<ProductListDto> update(
    @PathVariable UUID id,
    @Valid @RequestBody UpdateProductPayload payload
  ) {
    var result = productService.updateProduct(id, payload);
    return new ResponseEntity<>(
      result,
      HttpStatus.OK
    );
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> update(
    @PathVariable UUID id
  ) {
    productService.deleteProduct(id);
    return ResponseEntity.noContent().build();
  }
}
