package com.testlab.springdata.service;

import com.testlab.springdata.domain.order.Order;
import com.testlab.springdata.exception.NoResourceFoundException;
import com.testlab.springdata.repository.OrderRepository;
import com.testlab.springdata.statustype.OrderStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.testlab.springdata.statustype.OrderStatusType.*;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> all() {
        return orderRepository.findAll();
    }

    public Order createOrder(final Order order) {
        order.setOrderStatusType(IN_PROGRESS);
        return orderRepository.save(order);
    }

    public Order getOrderById(final Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NoResourceFoundException(id));
    }

    public Order updateOrder(final Long id, final Order order) {
        return orderRepository.findById(id)
                .map(order1 -> {
                    order1.setDescription(order.getDescription());
                    order1.setOrderStatusType(order.getOrderStatusType());
                    return orderRepository.save(order1);
                }).orElseThrow(() -> new NoResourceFoundException(id));
    }

    public void deleteOrder(final Long id) {

        final Order order = orderRepository.findById(id)
                .orElseThrow(() -> new NoResourceFoundException(id));
        if (order.getOrderStatusType().equals(IN_PROGRESS)) {
            order.setOrderStatusType(CANCELLED);
        }
        orderRepository.deleteById(id);
    }
}
