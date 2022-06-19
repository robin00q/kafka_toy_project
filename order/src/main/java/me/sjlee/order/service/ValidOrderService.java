package me.sjlee.order.service;

import lombok.RequiredArgsConstructor;
import me.sjlee.order.domain.models.Money;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.models.OrderStatus;
import me.sjlee.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ValidOrderService {

    private final OrderRepository orderRepository;

    public boolean valid(Long orderId, Integer money) {
        Optional<Order> findOrder = orderRepository.findOrderByIdAndTotalAmounts(orderId, new Money(money));
        return findOrder.isPresent() && findOrder.get().getStatus() == OrderStatus.PAYMENT_WAITING;
    }
}
