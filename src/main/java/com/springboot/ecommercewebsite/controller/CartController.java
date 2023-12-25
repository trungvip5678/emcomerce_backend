package com.springboot.ecommercewebsite.controller;

import com.springboot.ecommercewebsite.exception.ProductException;
import com.springboot.ecommercewebsite.exception.UserException;
import com.springboot.ecommercewebsite.model.Cart;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.request.AddItemRequest;
import com.springboot.ecommercewebsite.response.ApiResponse;
import com.springboot.ecommercewebsite.service.CartItemService;
import com.springboot.ecommercewebsite.service.CartService;
import com.springboot.ecommercewebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws UserException{
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user.getId());

        return new ResponseEntity<Cart>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<ApiResponse> addItemToCart(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt
            ) throws UserException, ProductException{
        User user = userService.findUserProfileByJwt(jwt);

        cartService.addCartItem(user.getId(),req);
        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart");
        res.setStatus(true);
        return new ResponseEntity<>(res,HttpStatus.OK);
    }


}
