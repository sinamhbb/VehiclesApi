package com.udacity.boogle.maps.api;

import com.udacity.boogle.maps.domain.address.Address;
;
import com.udacity.boogle.maps.service.MapService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/maps")
public class MapsController {

    private final MapService mapService;

    public MapsController(MapService mapService) {
        this.mapService = mapService;
    }

    @GetMapping
    public Address get(@RequestParam Long vehicleId) {
        return mapService.getAddressByVehicleId(vehicleId);

    }

    @PostMapping
    public Address post(@RequestParam Long vehicleId, @RequestParam Double lat, @RequestParam Double lon) {
        return mapService.saveAddress(vehicleId);
    }
}
