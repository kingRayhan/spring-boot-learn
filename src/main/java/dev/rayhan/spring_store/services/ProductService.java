package dev.rayhan.spring_store.services;

import dev.rayhan.spring_store.entities.Category;
import dev.rayhan.spring_store.entities.Product;
import dev.rayhan.spring_store.repositories.ProductRepository;
import dev.rayhan.spring_store.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void createProductWithCategory(){
        var category = Category.builder().name("category1").build();
        var product = Product
                .builder()
                .name("product1")
                .description("product1 desc")
                .price(100.0)
                .category(category)
                .build();

        productRepository.save(product);
    }

    @Transactional
    public void addProductToWishlistForUser(UUID userId, List<UUID> productIds){
        var products = productRepository.findAllById(productIds);
        Set<Product> productSet = StreamSupport
                .stream(products.spliterator(), false)
                .collect(Collectors.toSet());
        var user = userRepository.findById(userId).orElseThrow(RuntimeException::new);
        user.setFavoriteProducts(new HashSet<>(productSet));

        userRepository.save(user);
    }
}
