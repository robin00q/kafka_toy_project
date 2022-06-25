package me.sjlee.order.service;

import lombok.RequiredArgsConstructor;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.repository.OrderRepository;
import me.sjlee.order.infra.in.web.controller.StartOrderRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartOrderService {

    private final OrderRepository orderRepository;
    private final ProductApi productApi;
    private final UserApi userApi;

    public Long startOrder(StartOrderRequest startOrderRequest) {
        validateRequest(startOrderRequest);

        Order saved = orderRepository.save(startOrderRequest.toOrder());

        return saved.getId();
    }

    private void validateRequest(StartOrderRequest startOrderRequest) {
        if (!productApi.isValidProducts(startOrderRequest.toProductIds())) {
            throw new IllegalArgumentException("올바르지 않은 상품을 주문하였습니다.");
        }

        if (!userApi.isValidUser(startOrderRequest.toOrdererId())) {
            throw new IllegalStateException("올바르지 않은 주문자입니다.");
        }
    }
}
