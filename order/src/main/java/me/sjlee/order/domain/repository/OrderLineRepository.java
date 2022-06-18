package me.sjlee.order.domain.repository;

import me.sjlee.order.domain.models.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
}
