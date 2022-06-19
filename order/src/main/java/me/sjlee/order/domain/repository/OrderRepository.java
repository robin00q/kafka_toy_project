package me.sjlee.order.domain.repository;

import me.sjlee.order.domain.models.Money;
import me.sjlee.order.domain.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findOrderByIdAndTotalAmounts(Long orderId, Money amounts);
}
