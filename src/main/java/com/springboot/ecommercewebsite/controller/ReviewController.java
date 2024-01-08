package com.springboot.ecommercewebsite.controller;

import com.springboot.ecommercewebsite.exception.ProductException;
import com.springboot.ecommercewebsite.exception.UserException;
import com.springboot.ecommercewebsite.model.Review;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.request.ReviewRequest;
import com.springboot.ecommercewebsite.service.ReviewService;
import com.springboot.ecommercewebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Autowired
     private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createReview(
            @RequestBody ReviewRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws UserException, ProductException{
        User user =userService.findUserProfileByJwt(jwt);
        Review review = reviewService.createReview(req,user);

        return new ResponseEntity<Review>(review, HttpStatus.CREATED);

    }

    @GetMapping("/productId")
    public ResponseEntity<List<Review>> getProductsReview(
            @PathVariable Long productId
    )throws UserException, ProductException{
        List<Review> reviews = reviewService.getAllReview(productId);

        return new ResponseEntity<List<Review>>(reviews,HttpStatus.OK);
    }
}
