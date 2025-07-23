package dev.rayhan.spring_store.repositories;

import dev.rayhan.spring_store.entities.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ProductRepository extends CrudRepository<Product, UUID> {
}