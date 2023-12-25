package com.springboot.ecommercewebsite.repository;

import com.springboot.ecommercewebsite.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
