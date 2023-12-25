package com.springboot.ecommercewebsite.repository;

import com.springboot.ecommercewebsite.model.Order;
import com.springboot.ecommercewebsite.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
}
