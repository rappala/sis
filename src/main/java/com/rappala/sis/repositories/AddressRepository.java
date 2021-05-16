package com.rappala.sis.repositories;

import com.rappala.sis.entities.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface AddressRepository extends CrudRepository<Address, UUID> {
    Address findByStreetAddress(String name);
    boolean existsByAddressId(UUID id);
    boolean existsByStreetAddress(String name);
}

