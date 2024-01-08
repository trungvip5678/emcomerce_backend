package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.exception.ProductException;
import com.springboot.ecommercewebsite.model.Cart;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.request.AddItemRequest;

public interface CartService {
    public Cart createCart(User user);

    public String addCartItem(Long userId, AddItemRequest req) throws ProductException;

    public Cart findUserCart(Long userId);
}
