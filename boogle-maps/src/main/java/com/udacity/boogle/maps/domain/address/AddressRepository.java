package com.udacity.boogle.maps.domain.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> getAddressByVehicleId(Long vehicleId);
}
