package com.foodorder.repository;

import com.foodorder.model.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findByRestaurantId(Long restaurantId);
    List<FoodItem> findByAvailableTrue();
    
    @Query("SELECT f FROM FoodItem f WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :name, '%')) AND f.available = true")
    List<FoodItem> searchByName(@Param("name") String name);
    
    @Query("SELECT DISTINCT f FROM FoodItem f JOIN f.restaurant r WHERE LOWER(f.name) LIKE LOWER(CONCAT('%', :itemName, '%')) AND f.available = true AND r.active = true")
    List<FoodItem> searchFoodItemsAcrossRestaurants(@Param("itemName") String itemName);
}
