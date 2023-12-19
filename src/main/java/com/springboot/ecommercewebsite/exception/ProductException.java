package com.springboot.ecommercewebsite.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

//@AllArgsConstructor
//@NoArgsConstructor
public class ProductException extends Exception {
    public ProductException(String message){
        super(message);
    }

}
