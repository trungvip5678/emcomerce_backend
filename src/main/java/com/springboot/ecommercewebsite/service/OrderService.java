package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.exception.OrderException;
import com.springboot.ecommercewebsite.model.Address;
import com.springboot.ecommercewebsite.model.Order;
import com.springboot.ecommercewebsite.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address address);

    public Order findOrderById(Long orderId) throws OrderException;

    public List<Order> usersOrderHistory(Long userId);

    public Order placedOrder(Long orderId) throws OrderException;

    public Order confirmedOrder(Long orderId) throws OrderException;

    public Order shippedOrder(Long orderId) throws OrderException;

    public Order deliveredOrder(Long orderId) throws OrderException;

    public Order canceledOrder(Long orderId) throws  OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(Long orderId) throws OrderException;
}
