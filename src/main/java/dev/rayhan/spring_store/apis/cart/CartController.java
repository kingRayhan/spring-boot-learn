package dev.rayhan.spring_store.apis.cart;

import dev.rayhan.spring_store.apis.cart.dtos.CartDto;
import dev.rayhan.spring_store.apis.cart.dtos.CartItemDto;
import dev.rayhan.spring_store.apis.cart.dtos.CreateCartItemPayload;
import dev.rayhan.spring_store.apis.cart.entities.Cart;
import dev.rayhan.spring_store.apis.cart.entities.CartItem;
import dev.rayhan.spring_store.apis.cart.mappers.CartMapper;
import dev.rayhan.spring_store.apis.cart.repositories.CartRepository;
import dev.rayhan.spring_store.apis.cart.repositories.CartItemRepository;
import dev.rayhan.spring_store.apis.product.entities.Product;
import dev.rayhan.spring_store.apis.product.repositories.ProductRepository;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/carts")
@AllArgsConstructor
class CartController {
  private final CartMapper mapper;
  private final CartRepository cartRepository;
  private final ProductRepository productRepository;
  private final CartItemRepository cartItemRepository;

  @PostMapping("/")
  public ResponseEntity<?> store() {
    var cart = cartRepository.save(Cart.builder().build());
    return new ResponseEntity<>(mapper.toDto(cart), HttpStatus.CREATED);
  }

  @PostMapping("/{cartId}/items")
  public ResponseEntity<CartItemDto> storeCartItem(
    @PathVariable String cartId,
    @RequestBody CreateCartItemPayload payload
  ) {
    var product = productRepository.findById(payload.getProductId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    var cart = cartRepository.findById(UUID.fromString(cartId)).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart not found"));

    var cartItem = cart.getItems().stream().filter(item -> item.getProduct().getId().equals(product.getId())).findFirst().orElse(null);
    if (cartItem != null) {
      cartItem.setQuantity(cartItem.getQuantity() + 1);
    }else{
      cartItem = CartItem.builder().product(product).quantity(1).build();
    }
    cart.addItemToCart(cartItem);
    cartRepository.save(cart);

    var cartItemDto = mapper.toDto(cartItem);
    return new ResponseEntity<>(cartItemDto, HttpStatus.CREATED);
  }
}