package me.sjlee.payment.application.controller;

import lombok.Getter;
import me.sjlee.payment.domain.models.PayedBy;

@Getter
public class StartPaymentRequest {
    private Long orderId;
    private Integer amounts;
    private PayedBy payedBy;
}
