package me.sjlee.order.infra.in.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.sjlee.order.domain.models.Order;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class OrderDetailResponse {

    private long orderId;
    private long userId;
    private List<OrderLineDetailResponse> orderLines;

    @Getter
    @AllArgsConstructor
    public static class OrderLineDetailResponse {

        private long productId;
        private long optionId;
        private int quantity;
    }

    public static OrderDetailResponse from(Order order) {
        List<OrderLineDetailResponse> orderLineDetails = order.getOrderLines().stream()
                .map(e -> new OrderLineDetailResponse(e.getProductId(), e.getOptionId(), e.getQuantity()))
                .collect(Collectors.toList());

        return new OrderDetailResponse(order.getId(), order.getOrderer().getId(), orderLineDetails);
    }
}
