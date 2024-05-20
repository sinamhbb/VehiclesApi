package com.example.OrderingService.client.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import java.net.URI;
import java.util.List;

@Component
public class CarClient {
    private final WebClient client;
    private final URI baseUri = URI.create("http://localhost:8763/cars");



    public CarClient(WebClient client, ObjectMapper mapper) {
        this.client = client;
    }

    public List<EntityModel<Car>> getAllCars() {
        try {
            Traverson traverson = new Traverson(baseUri, MediaTypes.HAL_JSON);
            List<EntityModel<Car>> cars = traverson
                    .follow()
                    .toObject("$.content");
            return cars;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Car getCar(Long vehicleId) {
        try {
            Car car = client
                    .get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/cars/" + vehicleId)
                            .build())
                    .retrieve().bodyToMono(Car.class).block();
            return car;
        } catch (Exception e) {
            System.out.println("Cars service is down");
            return null;
        }
    }
}
