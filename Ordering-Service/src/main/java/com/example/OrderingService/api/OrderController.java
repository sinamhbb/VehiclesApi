package com.example.OrderingService.api;

import com.example.OrderingService.entity.SaleOrder;
import com.example.OrderingService.service.OrderService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderResourceAssembler assembler;

    public OrderController(OrderService orderService, OrderResourceAssembler assembler) {
        this.orderService = orderService;
        this.assembler = assembler;
    }

    @GetMapping
    CollectionModel<EntityModel<SaleOrder>> list() {
        List<EntityModel<SaleOrder>> resources = orderService.list().stream().map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(resources,linkTo(methodOn(OrderController.class).list()).withSelfRel());
    }

    @GetMapping("/{id}")
    EntityModel<SaleOrder> get(@PathVariable Long id) {
        SaleOrder saleOrder = orderService.findById(id);
        return assembler.toModel(saleOrder);
    }

    @PostMapping
    ResponseEntity<EntityModel<SaleOrder>> post(@Valid @RequestBody SaleOrder saleOrder) {
        SaleOrder saleOrderToSave = orderService.save(saleOrder);
        final URI uri = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{id}")
                .buildAndExpand(saleOrder.getId())
                .toUri();

        EntityModel<SaleOrder> resource = assembler.toModel(saleOrderToSave);
        return ResponseEntity.created(uri).body(resource);
    }

    @PutMapping("/{id}")
    ResponseEntity<EntityModel<SaleOrder>> put(@PathVariable Long id, @RequestBody SaleOrder saleOrder) {
        saleOrder.setId(id);
        SaleOrder saleOrderToSave = orderService.save(saleOrder);
        EntityModel<SaleOrder> resource = assembler.toModel(saleOrderToSave);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            orderService.delete(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
