package dev.rayhan.spring_store.apis.cart.repositories;

import dev.rayhan.spring_store.apis.cart.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CartItemRepository extends CrudRepository<CartItem, UUID> {
}