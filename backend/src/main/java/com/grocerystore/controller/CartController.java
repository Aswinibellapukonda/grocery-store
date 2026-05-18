package com.grocerystore.controller;

import com.grocerystore.entity.Cart;
import com.grocerystore.payload.request.CartRequest;
import com.grocerystore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    private String getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    @GetMapping
    public ResponseEntity<Cart> getCart() {
        return ResponseEntity.ok(cartService.getCartByUsername(getUsername()));
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(@RequestBody CartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(getUsername(), request.getProductId(), request.getQuantity()));
    }

    @DeleteMapping("/remove/{cartItemId}")
    public ResponseEntity<Cart> removeFromCart(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeFromCart(getUsername(), cartItemId));
    }
}
