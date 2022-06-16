package me.sjlee.order.service;

import lombok.RequiredArgsConstructor;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.repository.OrderRepository;
import me.sjlee.order.infra.in.web.controller.OrderRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartOrderService {

    private final OrderRepository orderRepository;

    public void startOrder(OrderRequest orderRequest) {
        // TODO : validate product, validate user
        Order order = orderRequest.toOrder();
        orderRepository.save(order);
    }
}
