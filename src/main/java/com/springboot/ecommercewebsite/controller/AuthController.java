package com.springboot.ecommercewebsite.controller;

import com.springboot.ecommercewebsite.exception.UserException;
import com.springboot.ecommercewebsite.model.Cart;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.repository.UserRepository;
import com.springboot.ecommercewebsite.request.LoginRequest;
import com.springboot.ecommercewebsite.response.AuthResponse;
import com.springboot.ecommercewebsite.security.JwtTokenProvider;
import com.springboot.ecommercewebsite.service.CartService;
import com.springboot.ecommercewebsite.service.CustomeUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private  UserRepository userRepository;
    private JwtTokenProvider jwtProvider;

    private  PasswordEncoder passwordEncoder;
    private CustomeUserServiceImpl customUserService;

    private CartService cartService;

    @Autowired
    public AuthController(UserRepository userRepository,PasswordEncoder passwordEncoder , JwtTokenProvider jwtProvider, CustomeUserServiceImpl customUserService,CartService cartService){
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.customUserService = customUserService;
        this.passwordEncoder = passwordEncoder;
        this.cartService = cartService;
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws Exception{
        String email = user.getEmail();
        String password = user.getPassword();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        System.out.println(user);

        User isEmailExist = userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email is Already used with another account");
        }

        User createdUser = new User();
        createdUser.setEmail(email);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setFirstName(firstName);
        createdUser.setLastName(lastName);

        User savedUSer = userRepository.save(createdUser);


        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUSer.getEmail(),savedUSer.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);
//        System.out.println("============ authen" + authentication);
        String token = jwtProvider.generateToken(authentication);
        AuthResponse authResponse = new AuthResponse(token, "Signup Success");
        Cart cart = cartService.createCart(savedUSer);
        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);

    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest){
        String username= loginRequest.getEmail();
        String password = loginRequest.getPassword();

        Authentication authentication = authenticate(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse(token, "Signin Success");

        return new ResponseEntity<>(authResponse, HttpStatus.CREATED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserService.loadUserByUsername(username);

        if(userDetails == null){
            throw new BadCredentialsException("Invalid Username");
        }

        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}
