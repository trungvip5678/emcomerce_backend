package com.springboot.ecommercewebsite.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthResponse {

    private String jwt;
    private String message;
}
