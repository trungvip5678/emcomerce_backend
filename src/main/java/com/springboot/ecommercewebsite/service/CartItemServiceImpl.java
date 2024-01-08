package com.springboot.ecommercewebsite.service;

import com.springboot.ecommercewebsite.exception.CartItemException;
import com.springboot.ecommercewebsite.exception.UserException;
import com.springboot.ecommercewebsite.model.Cart;
import com.springboot.ecommercewebsite.model.CartItem;
import com.springboot.ecommercewebsite.model.Product;
import com.springboot.ecommercewebsite.model.User;
import com.springboot.ecommercewebsite.repository.CartItemRepository;
import com.springboot.ecommercewebsite.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImpl implements CartItemService{
    private CartItemRepository cartItemRepository;

    private  UserService userService;

    private CartRepository cartRepository;
    public CartItemServiceImpl( CartItemRepository cartItemRepository,
                                UserService userService,
                                CartRepository cartRepository
                                ){
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.cartItemRepository = cartItemRepository;
    }
    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        CartItem createCartItem = cartItemRepository.save(cartItem);
        return createCartItem;
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(item.getUserId());

        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
//            item.setPrice(cartItem.getPrice());
            item.setPrice(item.getQuantity() * item.getProduct().getPrice());
            item.setDiscountedPrice(item.getProduct().getDiscountedPrice() * cartItem.getQuantity());

        }

        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, String memory, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart,product,memory,userId);

        return cartItem;
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
        CartItem cartItem= findCartItemById(cartItemId);
        User user = userService.findUserById(cartItem.getUserId());

        User reqUser = userService.findUserById(userId);

        if (user.getId().equals(reqUser.getId())){
            cartItemRepository.deleteById(cartItemId);
        }else {
            throw new UserException("You can't remove another users item");

        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> opt = cartItemRepository.findById(cartItemId);

        if (opt.isPresent()){
            return opt.get();
        }
        throw new CartItemException("CartItem not found with id: "+cartItemId);
    }
}
