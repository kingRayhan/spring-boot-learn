package dev.rayhan.spring_store.repositories;

import dev.rayhan.spring_store.entities.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CategoryRepository extends CrudRepository<Category, UUID> {
}