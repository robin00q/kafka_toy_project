package me.sjlee.payment.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.sjlee.payment.domain.models.Money;

@Getter
@AllArgsConstructor
public class PaymentFinishedEvent {
    private final long orderId;
    private final Money amount;
}
