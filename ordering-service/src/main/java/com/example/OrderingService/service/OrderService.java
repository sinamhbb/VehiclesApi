package com.example.OrderingService.service;


import com.example.OrderingService.client.car.CarClient;
import com.example.OrderingService.entity.SaleOrder;
import com.example.OrderingService.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CarClient carClient;

    public OrderService(OrderRepository orderRepository, CarClient carClient) {
        this.orderRepository = orderRepository;
        this.carClient = carClient;
    }

    public List<SaleOrder> list() {

        return orderRepository.findAll();
    }

    public SaleOrder findById(Long orderId) {
        Optional<SaleOrder> order = orderRepository.findById(orderId);
        order.orElseThrow();

        return order.get();
    }

    public SaleOrder save(SaleOrder saleOrder) {

        if (saleOrder.getVehicleId() != null || saleOrder.getVehicleId() != 0L) {
            saleOrder.setPrice(carClient.getCar(saleOrder.getVehicleId()).getPrice());
            return orderRepository.save(saleOrder);
        } else {
            throw new IllegalArgumentException("This Action is Illegal");
        }
    }

    public void delete(Long orderId) {
            Optional<SaleOrder> order = orderRepository.findById(orderId);
            order.orElseThrow();
            order.ifPresent(saleOrderToBeDeleted -> {
                orderRepository.deleteById(saleOrderToBeDeleted.getId());
            });
    }

}
