package com.udacity.pricing.service;

import com.udacity.pricing.entity.Price;
import com.udacity.pricing.repository.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Implements the pricing service to get prices for each vehicle.
 */
@Service
public class PricingService {

    @Autowired
    PriceRepository priceRepository;

    /**
     * Holds {ID: Price} pairings (current implementation allows for 20 vehicles)
     */
    private static final Map<Long, Price> PRICES = LongStream
            .range(1, 20)
            .mapToObj(i -> new Price("USD", randomPrice(), i))
            .collect(Collectors.toMap(Price::getVehicleId, p -> p));



    public Price savePrice(Long vehicleId) {
        Price price = PRICES.get(vehicleId);
        return priceRepository.save(price);
    }

    public Optional<Price> getPrice(Long vehicleId) throws PriceException {

        if (!PRICES.containsKey(vehicleId)) {
            throw new PriceException("Cannot find price for Vehicle " + vehicleId);
        }

        return Optional.ofNullable(priceRepository.findById(vehicleId).orElseThrow(() -> new PriceException("Cannot find price for Vehicle " + vehicleId)));
    }

    public void deletePrice(Long vehicleId) throws PriceException {
//        if (!PRICES.containsKey(vehicleId)) {
//            throw new PriceException("Cannot find price for Vehicle " + vehicleId);
//        }
        Optional<Price> priceToDelete = priceRepository.getPriceByVehicleId(vehicleId);
        priceToDelete.orElseThrow(() -> new PriceException("Cannot find price for Vehicle " + vehicleId));
        priceToDelete.ifPresent(price -> priceRepository.deleteById(price.getId()));
    }

    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }
}
