package com.springboot.ecommercewebsite.controller;

import com.springboot.ecommercewebsite.exception.OrderException;
import com.springboot.ecommercewebsite.exception.UserException;
import com.springboot.ecommercewebsite.model.Address;
import com.springboot.ecommercewebsite.model.Order;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.service.OrderService;
import com.springboot.ecommercewebsite.service.UserService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public ResponseEntity<Order> createOrder(
            @RequestBody Address shippingAddress,
            @RequestHeader("Authorization") String jwt
            )throws UserException{
        User user= userService.findUserProfileByJwt(jwt);
        System.out.println(user);
        Order order =orderService.createOrder(user,shippingAddress);
        System.out.println("order: "+ order);
        return new ResponseEntity<Order>(order, HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Order>> userOrderHistory(
           @RequestHeader("Authorization") String jwt
    )throws  UserException{
        User user = userService.findUserProfileByJwt(jwt);

        List<Order> orders = orderService.usersOrderHistory(user.getId());

        return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<Order> findOrderById(
            @PathVariable("Id") Long orderId,
            @RequestHeader("Authorization") String jwt
    ) throws UserException, OrderException{
        User user = userService.findUserProfileByJwt(jwt);

        Order order = orderService.findOrderById(orderId);

        return new ResponseEntity<>(order,HttpStatus.OK);
    }
}
