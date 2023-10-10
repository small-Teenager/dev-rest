package com.dev.rest.service;

import com.dev.rest.entity.doc.Logistics;
import com.dev.rest.entity.doc.Order;

import java.util.List;

public interface MongoDBService {

    Order addOrder(Order order);

    Boolean updateLogisticsByOrderId(Logistics logistics);

    Order getOrderById(Long orderId);

    /**
     *
     * @param page 页码
     * @param limit 步长
     * @return
     */
    List<Order> find(int page, int limit);
}
