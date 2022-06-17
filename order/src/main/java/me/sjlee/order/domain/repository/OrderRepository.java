package me.sjlee.order.domain.repository;

import me.sjlee.order.domain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
