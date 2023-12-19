package com.springboot.ecommercewebsite.exception;

import com.springboot.ecommercewebsite.service.CartItemService;

public class CartItemException extends Exception {
    public CartItemException(String message){
        super(message);
    }
}
