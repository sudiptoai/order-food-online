package com.foodorder.service;

import com.foodorder.dto.PlaceOrderDto;
import com.foodorder.enums.OrderStatus;
import com.foodorder.model.*;
import com.foodorder.pattern.OrderNotificationService;
import com.foodorder.pattern.PaymentStrategy;
import com.foodorder.pattern.PaymentStrategyFactory;
import com.foodorder.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final CartRepository cartRepository;
    private final PaymentStrategyFactory paymentStrategyFactory;
    private final OrderNotificationService notificationService;
    
    @Autowired
    public OrderService(OrderRepository orderRepository, UserRepository userRepository,
                        RestaurantRepository restaurantRepository, CartRepository cartRepository,
                        PaymentStrategyFactory paymentStrategyFactory, 
                        OrderNotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.cartRepository = cartRepository;
        this.paymentStrategyFactory = paymentStrategyFactory;
        this.notificationService = notificationService;
    }
    
    public Order placeOrder(PlaceOrderDto dto) {
        User customer = userRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));
        
        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        
        Cart cart = cartRepository.findByCustomerId(dto.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Cart is empty"));
        
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        
        // Validate all items belong to the same restaurant
        boolean allFromSameRestaurant = cart.getItems().stream()
                .allMatch(item -> item.getFoodItem().getRestaurant().getId().equals(dto.getRestaurantId()));
        
        if (!allFromSameRestaurant) {
            throw new IllegalArgumentException("All items must be from the same restaurant");
        }
        
        Double totalAmount = cart.getTotalAmount();
        
        // Process payment using Strategy pattern
        PaymentStrategy paymentStrategy = paymentStrategyFactory.getStrategy(dto.getPaymentMethod());
        boolean paymentSuccess = paymentStrategy.processPayment(totalAmount, dto.getPaymentMethod());
        
        if (!paymentSuccess) {
            throw new IllegalStateException("Payment failed");
        }
        
        // Create order
        Order order = new Order();
        order.setCustomer(customer);
        order.setRestaurant(restaurant);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setTotalAmount(totalAmount);
        order.setDeliveryAddress(dto.getDeliveryAddress());
        
        // Copy cart items to order items
        for (CartItem cartItem : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setFoodItem(cartItem.getFoodItem());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(cartItem.getFoodItem().getPrice());
            order.getItems().add(orderItem);
        }
        
        Order savedOrder = orderRepository.save(order);
        
        // Clear cart after order
        cart.getItems().clear();
        cartRepository.save(cart);
        
        // Notify observers using Observer pattern
        notificationService.notifyObservers(savedOrder);
        
        return savedOrder;
    }
    
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
    
    public List<Order> getOrdersByCustomer(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    public List<Order> getOrdersByRestaurant(Long restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }
    
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }
    
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        order.setStatus(newStatus);
        Order updatedOrder = orderRepository.save(order);
        
        // Notify observers about status change
        notificationService.notifyObservers(updatedOrder);
        
        return updatedOrder;
    }
    
    public Order assignDeliveryPerson(Long orderId, Long deliveryPersonId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        
        User deliveryPerson = userRepository.findById(deliveryPersonId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery person not found"));
        
        order.setDeliveryPerson(deliveryPerson);
        order.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        
        Order updatedOrder = orderRepository.save(order);
        notificationService.notifyObservers(updatedOrder);
        
        return updatedOrder;
    }
    
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
