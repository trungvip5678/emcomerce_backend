package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.model.OrderItem;
import com.springboot.ecommercewebsite.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
            return orderItemRepository.save(orderItem);
    }
}
