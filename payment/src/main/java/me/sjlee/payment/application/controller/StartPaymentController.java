package me.sjlee.payment.application.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.payment.application.service.StartPaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StartPaymentController {

    private final StartPaymentService startPaymentService;

    @PostMapping("/pay/order")
    public Long startPayment(@RequestBody StartPaymentRequest request) {
        return startPaymentService.startPayment(request);
    }
}
