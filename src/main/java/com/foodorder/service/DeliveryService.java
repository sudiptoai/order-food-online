package com.foodorder.service;

import com.foodorder.enums.DeliveryStatus;
import com.foodorder.model.DeliveryPerson;
import com.foodorder.model.Order;
import com.foodorder.model.User;
import com.foodorder.repository.DeliveryPersonRepository;
import com.foodorder.repository.OrderRepository;
import com.foodorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeliveryService {
    
    private final DeliveryPersonRepository deliveryPersonRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    
    @Autowired
    public DeliveryService(DeliveryPersonRepository deliveryPersonRepository,
                           UserRepository userRepository, OrderRepository orderRepository) {
        this.deliveryPersonRepository = deliveryPersonRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }
    
    public DeliveryPerson registerDeliveryPerson(Long userId, String vehicleNumber) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        
        DeliveryPerson deliveryPerson = new DeliveryPerson();
        deliveryPerson.setUser(user);
        deliveryPerson.setVehicleNumber(vehicleNumber);
        deliveryPerson.setStatus(DeliveryStatus.AVAILABLE);
        
        return deliveryPersonRepository.save(deliveryPerson);
    }
    
    public List<DeliveryPerson> getAvailableDeliveryPersons() {
        return deliveryPersonRepository.findByStatus(DeliveryStatus.AVAILABLE);
    }
    
    public DeliveryPerson updateDeliveryStatus(Long deliveryPersonId, DeliveryStatus status) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery person not found"));
        
        deliveryPerson.setStatus(status);
        return deliveryPersonRepository.save(deliveryPerson);
    }
    
    public DeliveryPerson updateLocation(Long deliveryPersonId, String location) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery person not found"));
        
        deliveryPerson.setCurrentLocation(location);
        return deliveryPersonRepository.save(deliveryPerson);
    }
    
    public List<Order> getDeliveryPersonOrders(Long deliveryPersonId) {
        DeliveryPerson deliveryPerson = deliveryPersonRepository.findById(deliveryPersonId)
                .orElseThrow(() -> new IllegalArgumentException("Delivery person not found"));
        
        return orderRepository.findByDeliveryPersonId(deliveryPerson.getUser().getId());
    }
    
    public Optional<DeliveryPerson> getDeliveryPersonById(Long id) {
        return deliveryPersonRepository.findById(id);
    }
    
    public List<DeliveryPerson> getAllDeliveryPersons() {
        return deliveryPersonRepository.findAll();
    }
}
