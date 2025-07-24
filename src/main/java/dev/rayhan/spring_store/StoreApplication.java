package dev.rayhan.spring_store;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StoreApplication {
    public static void main(String[] args) {
        SpringApplication.run(StoreApplication.class, args);
//        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);

        
//        UnifiedJedis jedis = new UnifiedJedis("redis://localhost:6379");
//        jedis.set("name", "rayhan");
//        jedis.close();

//        --- User
//        var userRepository = context.getBean(UserRepository.class);
//        var userService = context.getBean(UserService.class);
//        userService.createUserWithRelatedData();
//        userService.deleteRelatedData();
//        userRepository.deleteById(UUID.fromString("ec9c8116-c343-440a-a77f-fa2b3acb69de"));

//        --- Product & Category
//        var productService = context.getBean(ProductService.class);
//        var productRepository = context.getBean(ProductRepository.class);
//        productService.createProductWithCategory();
//        productService.addProductToWishlistForUser(
//                UUID.fromString("9df56684-2a22-4dd7-bc90-09669035cfba"),
//                List.of(
//                        UUID.fromString("7798d9f1-be0f-4ab1-b4dd-f406f0201365"),
//                        UUID.fromString("9b80320b-d5a0-48ce-b82e-935437a1e1ac"),
//                        UUID.fromString("be49389b-d58c-4931-b21d-b3fb771d04fe")
//                )
//        );
//        var products = productRepository.getProductsWithinPriceRange(0.0, 500.0);
//        products.forEach(System.out::println);
//        productRepository.updateProductPriceById(UUID.fromString("7798d9f1-be0f-4ab1-b4dd-f406f0201365"), 90.0);
//        var products = productRepository.getProjectionProducts();
//        products.forEach(productListDto -> {
//            System.out.println(productListDto.getName());
//        });
    }
}