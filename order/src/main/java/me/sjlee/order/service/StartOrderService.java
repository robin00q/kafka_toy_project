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
    private final ProductApi productApi;
    private final UserApi userApi;

    public void startOrder(OrderRequest orderRequest) {
        validateRequest(orderRequest);

        Order order = orderRequest.toOrder();
        orderRepository.save(order);
    }

    private void validateRequest(OrderRequest orderRequest) {
        if (!productApi.isValidProducts(orderRequest.getProductIds())) {
            throw new IllegalArgumentException("올바르지 않은 상품을 주문하였습니다.");
        }

        if (!userApi.isValidUser(orderRequest.getOrdererId())) {
            throw new IllegalStateException("올바르지 않은 주문자입니다.");
        }
    }
}
