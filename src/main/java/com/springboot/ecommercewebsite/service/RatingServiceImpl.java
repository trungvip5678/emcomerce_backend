package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.exception.ProductException;
import com.springboot.ecommercewebsite.model.Product;
import com.springboot.ecommercewebsite.model.Rating;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.repository.RatingRepository;
import com.springboot.ecommercewebsite.request.RatingRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class RatingServiceImpl implements  RatingService{
    private RatingRepository ratingRepository;
    private ProductService productService;
    @Override
    public Rating createRating(RatingRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Rating rating = new Rating();
        rating.setRating(req.getRating());
        rating.setUser(user);
        rating.setProduct(product);
        rating.setCreateAt(LocalDateTime.now());

        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getProductsRating(Long productId) {
        return ratingRepository.getAllProductsRating(productId);
    }
}
