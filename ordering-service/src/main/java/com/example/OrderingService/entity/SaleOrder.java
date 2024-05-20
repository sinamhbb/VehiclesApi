package com.example.OrderingService.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class SaleOrder {

    @Id
    @GeneratedValue
    private Long id;
    @CreatedDate
    private LocalDateTime orderDate;
    private Long vehicleId;
    private String price;
    private String customerName;

    public SaleOrder() {
    }

    public SaleOrder(Long id, LocalDateTime orderDate, Long vehicleId, String price, String customerName) {
        this.id = id;
        this.orderDate = orderDate;
        this.vehicleId = vehicleId;
        this.price = price;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
