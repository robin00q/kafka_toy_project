package me.sjlee.payment.infra.out.queue;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentFinishedRecord {

    private final long orderId;
    private final Integer amount;
}
