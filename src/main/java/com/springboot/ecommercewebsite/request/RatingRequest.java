package com.springboot.ecommercewebsite.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RatingRequest {

    private Long productId;

    private double rating;
}
