package com.testlab.springdata.helper;

import com.testlab.springdata.controller.OrderController;
import com.testlab.springdata.domain.order.Order;
import com.testlab.springdata.statustype.OrderStatusType;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class OrderResourceAssembler implements ResourceAssembler<Order, Resource<Order>> {
    @Override
    public Resource<Order> toResource(final Order order) {

        final Resource<Order> orderResource = new Resource<>(order,
                linkTo(methodOn(OrderController.class).getASingleOrder(order.getId())).withSelfRel(),
                linkTo(methodOn(OrderController.class).getallOrders()).withRel("orders"));

        if (order.getOrderStatusType().equals(OrderStatusType.IN_PROGRESS)) {
            orderResource.add(linkTo(methodOn(OrderController.class).cancelOrder(order.getId())).withRel("cancel"));
            orderResource.add(linkTo(methodOn(OrderController.class).updateOrder(order.getId(), order)).withRel("complete"));
        }

        return orderResource;
    }
}
