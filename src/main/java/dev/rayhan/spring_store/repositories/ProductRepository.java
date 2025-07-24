package dev.rayhan.spring_store.repositories;

import dev.rayhan.spring_store.entities.Category;
import dev.rayhan.spring_store.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findAllByCategory(Category category, Pageable pageable);
    
//    @Query(value = "SELECT p.* FROM products p WHERE p.price BETWEEN :min AND :max", nativeQuery = true)
//    public List<Product> getProductsWithinPriceRange(
//            @Param("min") Double min, @Param("max") Double max
//    );
//
//    @Modifying
//    @Transactional
//    @Query("update Product p set p.price = :price where p.id = :productId")
//    void updateProductPriceById(
//            @Param("productId") UUID productId,
//            @Param("price") Double price
//    );
//
//    @Query("select p.id, p.name from Product p")
//    List<ProductListDto> getProjectionProducts();
}