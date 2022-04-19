package com.udacity.boogle.maps.api;

import com.udacity.boogle.maps.domain.address.Address;
;
import com.udacity.boogle.maps.service.MapService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping
    public HttpStatus delete(@RequestParam Long vehicleId) {
        try {
            System.out.println("Request received : " + vehicleId);
            mapService.deleteAddressByVehicleId(vehicleId);
            return HttpStatus.OK;
        } catch (Exception e) {
            return HttpStatus.BAD_REQUEST;
        }
    }
}
