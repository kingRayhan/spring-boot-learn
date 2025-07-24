package dev.rayhan.spring_store.controllers;

import dev.rayhan.spring_store.common.PaginationHelper;
import dev.rayhan.spring_store.common.ValidationErrorHandler;
import dev.rayhan.spring_store.dtos.ProductListFilterRequestQueryParam;
import dev.rayhan.spring_store.entities.Category;
import dev.rayhan.spring_store.mappers.ProductListDtoMapper;
import dev.rayhan.spring_store.repositories.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductListDtoMapper productListDtoMapper;

    public ProductController(ProductRepository productRepository, ProductListDtoMapper productListDtoMapper) {
        this.productRepository = productRepository;
        this.productListDtoMapper = productListDtoMapper;
    }

    @GetMapping("/")
    public ResponseEntity<?> getAllProducts(
            @Valid ProductListFilterRequestQueryParam filter,
            BindingResult result
    ) {

        var errors = ValidationErrorHandler.handleValidationErrors(result);
        if (errors != null) {
            return ResponseEntity.badRequest().body(errors);
        }

        if (filter.getCategoryId() != null) {
            var category = Category.builder().id(filter.getCategoryId()).build();
            return ResponseEntity.ok(
                    productRepository.findAllByCategory(category, PaginationHelper.createPageable(
                                    filter.getPage(),
                                    filter.getLimit(),
                                    filter.getSort(),
                                    filter.getSortBy().toString()
                            )).stream()
                            .map(productListDtoMapper::toDto).toList()
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
                        ).stream()
                        .map(productListDtoMapper::toDto).toList()
        );
    }
}
