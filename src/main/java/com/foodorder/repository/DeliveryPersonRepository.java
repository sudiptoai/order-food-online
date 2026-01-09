package com.foodorder.repository;

import com.foodorder.enums.DeliveryStatus;
import com.foodorder.model.DeliveryPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryPersonRepository extends JpaRepository<DeliveryPerson, Long> {
    Optional<DeliveryPerson> findByUserId(Long userId);
    List<DeliveryPerson> findByStatus(DeliveryStatus status);
}
