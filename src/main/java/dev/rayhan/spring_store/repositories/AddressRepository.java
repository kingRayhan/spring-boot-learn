package dev.rayhan.spring_store.repositories;

import dev.rayhan.spring_store.common.entities.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {
}