package dev.rayhan.spring_store.apis.cart.repositories;

import dev.rayhan.spring_store.apis.cart.entities.Cart;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CartRepository extends CrudRepository<Cart, UUID> {
}