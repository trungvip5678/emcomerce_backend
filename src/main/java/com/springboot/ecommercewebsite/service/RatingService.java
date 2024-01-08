package com.springboot.ecommercewebsite.service;


import com.springboot.ecommercewebsite.exception.ProductException;
import com.springboot.ecommercewebsite.model.Rating;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.request.RatingRequest;

import java.util.List;

public interface RatingService {

    public Rating createRating(RatingRequest req, User user) throws ProductException;

    public List<Rating> getProductsRating(Long productId);
}
