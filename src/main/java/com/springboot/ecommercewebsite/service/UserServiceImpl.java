package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.exception.UserException;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.repository.UserRepository;
import com.springboot.ecommercewebsite.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,  JwtTokenProvider jwtTokenProvider){
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User findUserById(Long userId) throws UserException {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            return user.get();
        }

        throw new UserException("user not found with id - "+userId);
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtTokenProvider.getUsername(jwt.substring(7));
        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new UserException("user not found with email "+ email);
        }

        return user;
    }
}
