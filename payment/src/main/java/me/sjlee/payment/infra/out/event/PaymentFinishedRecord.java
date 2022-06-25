package me.sjlee.payment.infra.out.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentFinishedRecord {

    private final long orderId;
    private final Integer amount;
}
