package com.example.OrderingService.client.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.hateoas.client.Hop.rel;

@Component
public class CarClient {
    private final WebClient client;
    private final URI baseUri = URI.create("http://localhost:8763/cars");



    public CarClient(WebClient client, ObjectMapper mapper) {
        this.client = client;
    }

    public List<EntityModel<Car>> getAllCars() {
        try {
//            TypeReferences.CollectionModelType<EntityModel<Car>> objects = client
//                    .get()
//                    .uri(uriBuilder -> uriBuilder
//                            .path("/cars/")
////                            .queryParam("vehicleId", vehicleId)
//                            .build())
//                    .retrieve().bodyToMono(new ParameterizedTypeReference<TypeReferences.CollectionModelType<EntityModel<Car>>>() {
//                    }).block();
//
//            List<Car> cars = Arrays.stream(objects.getBody().get(1).getId())
//                    .map(object -> mapper.convertValue(object,Car.class))
//                    .collect(Collectors.toList());

//            System.out.println("car: " + objects.get(1).getId());
            Traverson traverson = new Traverson(baseUri, MediaTypes.HAL_JSON);
            List<EntityModel<Car>> cars = traverson
                    .follow()
                    .toObject("$.content");
            System.out.println("Cars : " + cars.size());
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
//                            .queryParam("vehicleId", vehicleId)
                            .build())
                    .retrieve().bodyToMono(Car.class).block();
            System.out.println("car: " + car.getPrice());
            return car;
        } catch (Exception e) {
            System.out.println("Cars service is down");
            return null;
        }
    }
}
