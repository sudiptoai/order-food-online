package com.foodorder.model;

import com.foodorder.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "delivery_persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryPerson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status = DeliveryStatus.AVAILABLE;
    
    private String currentLocation;
    
    private String vehicleNumber;
}
