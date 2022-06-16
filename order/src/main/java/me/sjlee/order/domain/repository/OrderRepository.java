package me.sjlee.order.domain.repository;

import me.sjlee.order.domain.models.Order;

public interface OrderRepository {

    Order save(Order order);
}
