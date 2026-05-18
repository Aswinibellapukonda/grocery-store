package com.grocerystore.service;

import com.grocerystore.entity.Cart;
import com.grocerystore.entity.CartItem;
import com.grocerystore.entity.Product;
import com.grocerystore.entity.User;
import com.grocerystore.repository.CartRepository;
import com.grocerystore.repository.ProductRepository;
import com.grocerystore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public Cart getCartByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return cartRepository.findByUser(user).orElseGet(() -> {
            Cart cart = new Cart();
            cart.setUser(user);
            return cartRepository.save(cart);
        });
    }

    @Transactional
    public Cart addToCart(String username, Long productId, Integer quantity) {
        Cart cart = getCartByUsername(username);
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        
        Optional<CartItem> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getCartItems().add(newItem);
        }
        return cartRepository.save(cart);
    }

    @Transactional
    public Cart removeFromCart(String username, Long cartItemId) {
        Cart cart = getCartByUsername(username);
        cart.getCartItems().removeIf(item -> item.getId().equals(cartItemId));
        return cartRepository.save(cart);
    }

    @Transactional
    public void clearCart(String username) {
        Cart cart = getCartByUsername(username);
        cart.getCartItems().clear();
        cartRepository.save(cart);
    }
}
