package com.example.OrderingService.repository;

import com.example.OrderingService.entity.SaleOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<SaleOrder, Long> {
}
