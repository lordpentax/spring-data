package com.testlab.springdata.controller;

import com.testlab.springdata.domain.order.Order;
import com.testlab.springdata.helper.OrderResourceAssembler;
import com.testlab.springdata.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("api/v1")
public class OrderController {

    private final OrderService orderService;
    private final OrderResourceAssembler orderResourceAssembler;

    @Autowired
    public OrderController(final OrderService orderService, final OrderResourceAssembler orderResourceAssembler) {
        this.orderService = orderService;
        this.orderResourceAssembler = orderResourceAssembler;
    }

    @PostMapping("/orders")
    public Resource getallOrders() {
        return new Resource(orderService.all()
                .stream()
                .map(order -> new Resource(orderResourceAssembler.toResource(order))));
    }

    @GetMapping("/orders/{id}/order")
    public Resource<Order> getASingleOrder(@PathVariable final Long id) {
        return new Resource(orderResourceAssembler.toResource(orderService.getOrderById(id)));
    }

    @PostMapping("/orders")
    public Resource newOrder(@RequestBody final Order order) {
        return new Resource(orderResourceAssembler.toResource(order));
    }

    @PutMapping("/orders/{id}/complete")
    public Resource updateOrder(@PathVariable final Long id, @RequestBody final Order order) {

        return new Resource(order,
                linkTo(methodOn(OrderController.class).updateOrder(id, order)).withSelfRel(),
                linkTo(methodOn(OrderController.class).getallOrders()).withRel("orders"));
    }

    @DeleteMapping("/orders/{id}/cancel")
    public Resource cancelOrder(@PathVariable final Long id) {
        orderService.deleteOrder(id);
        return new Resource(linkTo(methodOn(OrderController.class).getallOrders()).withRel("orders"));
    }
}
