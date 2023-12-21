package com.springboot.ecommercewebsite.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddItemRequest {
    private Long productId;

    private String memory;

    private int quantity;

    private Integer price;


}
