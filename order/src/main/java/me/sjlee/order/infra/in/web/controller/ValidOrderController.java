package me.sjlee.order.infra.in.web.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.order.service.ValidOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidOrderController {

    private final ValidOrderService validOrderService;

    @GetMapping("/order/valid")
    public boolean valid(@RequestParam Long orderId,
                         @RequestParam Integer amounts) {
        return validOrderService.valid(orderId, amounts);
    }
}
