package dev.rayhan.spring_store.repositories;

import dev.rayhan.spring_store.dtos.ProductListDto;
import dev.rayhan.spring_store.entities.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {

    @Query(value = "SELECT p.* FROM products p WHERE p.price BETWEEN :min AND :max", nativeQuery = true)
    public List<Product> getProductsWithinPriceRange(
            @Param("min") Double min, @Param("max") Double max
    );

    @Modifying
    @Transactional
    @Query("update Product p set p.price = :price where p.id = :productId")
    void updateProductPriceById(
            @Param("productId") UUID productId,
            @Param("price") Double price
    );

    @Query("select p.id, p.name from Product p")
    List<ProductListDto> getProjectionProducts();
}