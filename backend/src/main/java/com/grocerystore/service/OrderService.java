package com.grocerystore.service;

import com.grocerystore.entity.Cart;
import com.grocerystore.entity.Order;
import com.grocerystore.entity.OrderItem;
import com.grocerystore.entity.User;
import com.grocerystore.repository.OrderRepository;
import com.grocerystore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Order placeOrder(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartService.getCartByUsername(username);

        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus("COMPLETED");
        
        double totalAmount = 0;
        for (var cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getProduct().getPrice());
            order.getOrderItems().add(orderItem);
            totalAmount += (cartItem.getProduct().getPrice() * cartItem.getQuantity());
        }
        order.setTotalAmount(totalAmount);
        
        Order savedOrder = orderRepository.save(order);
        cartService.clearCart(username);
        return savedOrder;
    }

    public List<Order> getUserOrders(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findByUserOrderByOrderDateDesc(user);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
