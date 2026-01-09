package com.foodorder.repository;

import com.foodorder.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    List<Restaurant> findByActiveTrue();
    List<Restaurant> findByNameContainingIgnoreCase(String name);
    List<Restaurant> findByLocationContainingIgnoreCase(String location);
    
    @Query("SELECT r FROM Restaurant r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(r.location) LIKE LOWER(CONCAT('%', :location, '%')) AND r.active = true")
    List<Restaurant> searchByNameAndLocation(@Param("name") String name, @Param("location") String location);
}
