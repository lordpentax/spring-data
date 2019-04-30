package com.testlab.springdata.domain.order;

import com.testlab.springdata.statustype.OrderStatusType;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "ORDER_CUSTOMER")
public class Order {

    private Long Id;
    private String description;

    @Enumerated
    private OrderStatusType orderStatusType;
}
