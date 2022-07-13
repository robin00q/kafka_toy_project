package me.sjlee.order.infra.in.web.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.order.domain.models.OrderStatus;
import me.sjlee.order.infra.in.web.controller.dto.StartOrderRequest;
import me.sjlee.order.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order/start")
    public Long startOrder(@RequestBody StartOrderRequest startOrderRequest) {
        return orderService.startOrder(startOrderRequest);
    }

    @GetMapping("/order/status")
    public OrderStatus getStatus(@RequestParam long orderId) {
        return orderService.getStatus(orderId);
    }
}
