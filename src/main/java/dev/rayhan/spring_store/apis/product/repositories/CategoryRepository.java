package dev.rayhan.spring_store.apis.product.repositories;

import dev.rayhan.spring_store.common.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
}