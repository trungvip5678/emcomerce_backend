package com.springboot.ecommercewebsite.repository;

import com.springboot.ecommercewebsite.model.Cart;
import com.springboot.ecommercewebsite.model.CartItem;
import com.springboot.ecommercewebsite.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    @Query("SELECT ci FROM CartItem ci WHERE ci.cart = :cart AND ci.product = :product" +
            " AND ci.memory=:memory AND ci.userId=:userId")
    public CartItem isCartItemExist(@Param("cart")Cart cart, @Param("product")Product product,
                                    @Param("memory") String memory, @Param("userId") Long userId);
}
