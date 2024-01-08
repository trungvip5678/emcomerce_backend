package com.springboot.ecommercewebsite.repository;

import com.springboot.ecommercewebsite.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User,Long> {
//    User loadUserByUsername(String username);

//    Boolean existsByEmail(String email);
//
//    @Query ("SELECT u from User u WHERE u.email = :email")
    public User findByEmail( String email);
//
//    boolean existsByUsername(String username);
}
