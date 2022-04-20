package com.udacity.pricing.repository;

import com.udacity.pricing.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PriceRepository extends JpaRepository<Price,Long> {
    Optional<Price> getPriceByVehicleId(Long vehicleId);
}