package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.exception.UserException;
import com.springboot.ecommercewebsite.model.User;

public interface UserService {
    public User findUserById(Long userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;
}
