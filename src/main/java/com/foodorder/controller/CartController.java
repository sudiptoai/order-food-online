package com.foodorder.controller;

import com.foodorder.dto.AddToCartDto;
import com.foodorder.model.Cart;
import com.foodorder.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    private final CartService cartService;
    
    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }
    
    @GetMapping("/{customerId}")
    public ResponseEntity<Cart> getCart(@PathVariable Long customerId) {
        return cartService.getCart(customerId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/add")
    public ResponseEntity<Cart> addItemToCart(@RequestBody AddToCartDto dto) {
        Cart cart = cartService.addItemToCart(dto);
        return ResponseEntity.ok(cart);
    }
    
    @DeleteMapping("/{customerId}/item/{foodItemId}")
    public ResponseEntity<Cart> removeItemFromCart(@PathVariable Long customerId, @PathVariable Long foodItemId) {
        Cart cart = cartService.removeItemFromCart(customerId, foodItemId);
        return ResponseEntity.ok(cart);
    }
    
    @DeleteMapping("/{customerId}/clear")
    public ResponseEntity<Void> clearCart(@PathVariable Long customerId) {
        cartService.clearCart(customerId);
        return ResponseEntity.ok().build();
    }
}
