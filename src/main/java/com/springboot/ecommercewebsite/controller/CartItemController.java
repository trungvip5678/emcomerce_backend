package com.springboot.ecommercewebsite.controller;

import com.springboot.ecommercewebsite.exception.CartItemException;
import com.springboot.ecommercewebsite.exception.UserException;
import com.springboot.ecommercewebsite.model.CartItem;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.response.ApiResponse;
import com.springboot.ecommercewebsite.service.CartItemService;
import com.springboot.ecommercewebsite.service.CartService;
import com.springboot.ecommercewebsite.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemService cartItemService;

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItem(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt
    ) throws UserException, CartItemException{
        User user = userService.findUserProfileByJwt(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Deleted item from cart");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItem(
            @RequestBody CartItem cartItem,
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt
    ) throws  UserException, CartItemException{
        User use = userService.findUserProfileByJwt(jwt);

        CartItem updatedCartItem = cartItemService.updateCartItem(use.getId(), cartItemId,cartItem);

        return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
    }
}
