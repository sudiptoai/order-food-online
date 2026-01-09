package com.foodorder.service;

import com.foodorder.dto.AddToCartDto;
import com.foodorder.model.Cart;
import com.foodorder.model.CartItem;
import com.foodorder.model.FoodItem;
import com.foodorder.model.User;
import com.foodorder.repository.CartItemRepository;
import com.foodorder.repository.CartRepository;
import com.foodorder.repository.FoodItemRepository;
import com.foodorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartService {
    
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final FoodItemRepository foodItemRepository;
    
    @Autowired
    public CartService(CartRepository cartRepository, CartItemRepository cartItemRepository,
                       UserRepository userRepository, FoodItemRepository foodItemRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
        this.foodItemRepository = foodItemRepository;
    }
    
    public Cart getOrCreateCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId)
                .orElseGet(() -> {
                    User customer = userRepository.findById(customerId)
                            .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
                    Cart cart = new Cart();
                    cart.setCustomer(customer);
                    return cartRepository.save(cart);
                });
    }
    
    public Cart addItemToCart(AddToCartDto dto) {
        Cart cart = getOrCreateCart(dto.getCustomerId());
        FoodItem foodItem = foodItemRepository.findById(dto.getFoodItemId())
                .orElseThrow(() -> new IllegalArgumentException("Food item not found"));
        
        if (!foodItem.getAvailable()) {
            throw new IllegalArgumentException("Food item is not available");
        }
        
        Optional<CartItem> existingItem = cartItemRepository.findByCartIdAndFoodItemId(
                cart.getId(), dto.getFoodItemId());
        
        if (existingItem.isPresent()) {
            CartItem cartItem = existingItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + dto.getQuantity());
            cartItemRepository.save(cartItem);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setFoodItem(foodItem);
            cartItem.setQuantity(dto.getQuantity());
            cart.getItems().add(cartItem);
            cartItemRepository.save(cartItem);
        }
        
        return cartRepository.save(cart);
    }
    
    public Cart removeItemFromCart(Long customerId, Long foodItemId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        
        CartItem cartItem = cartItemRepository.findByCartIdAndFoodItemId(cart.getId(), foodItemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));
        
        cart.getItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        
        return cartRepository.save(cart);
    }
    
    public void clearCart(Long customerId) {
        Cart cart = cartRepository.findByCustomerId(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        
        cart.getItems().clear();
        cartRepository.save(cart);
    }
    
    public Optional<Cart> getCart(Long customerId) {
        return cartRepository.findByCustomerId(customerId);
    }
}
