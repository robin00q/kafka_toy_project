package me.sjlee.payment.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentFinishedEvent {
    private long orderId;
}
