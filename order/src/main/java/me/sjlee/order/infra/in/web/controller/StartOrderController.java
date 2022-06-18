package me.sjlee.order.infra.in.web.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.order.service.StartOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StartOrderController {

    private final StartOrderService startOrderService;

    @PostMapping("/order/start")
    public Long startOrder(@RequestBody StartOrderRequest startOrderRequest) {
        return startOrderService.startOrder(startOrderRequest);
    }
}
