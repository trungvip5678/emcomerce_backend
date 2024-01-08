package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.exception.ProductException;
import com.springboot.ecommercewebsite.model.Product;
import com.springboot.ecommercewebsite.model.Review;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.repository.ProductRepository;
import com.springboot.ecommercewebsite.repository.ReviewRepository;
import com.springboot.ecommercewebsite.request.ReviewRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewServiceImpl implements  ReviewService{
    private ReviewRepository reviewRepository;
    private ProductService productService;

    public ReviewServiceImpl(ReviewRepository reviewRepository,ProductService productService){
        this.reviewRepository = reviewRepository;
        this.productService = productService;
    }
    @Override
    public Review createReview(ReviewRequest req, User user) throws ProductException {
        Product product = productService.findProductById(req.getProductId());

        Review review = new Review();
        review.setUser(user);
        review.setReview(req.getReview());
        review.setProduct(product);
        review.setCreateAt(LocalDateTime.now());


        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReview(Long productId) {
        return reviewRepository.getAllProductsReview(productId);
    }
}
