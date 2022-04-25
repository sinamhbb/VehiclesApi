package com.example.OrderingService.api;

import com.example.OrderingService.entity.SaleOrder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class OrderResourceAssembler implements RepresentationModelAssembler<SaleOrder, EntityModel<SaleOrder>> {

    @Override
    public EntityModel<SaleOrder> toModel(SaleOrder saleOrder) {
        return EntityModel.of(saleOrder,
                linkTo(methodOn(OrderController.class).get(saleOrder.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).list()).withRel("cars"));
    }
}
