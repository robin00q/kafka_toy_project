package me.sjlee.payment.infra.out.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderPaymentFinishedRecord {

    private final long orderId;
    private final Integer amount;
}
