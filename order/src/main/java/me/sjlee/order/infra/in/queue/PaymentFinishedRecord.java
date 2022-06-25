package me.sjlee.order.infra.in.queue;

import lombok.Getter;

@Getter
public class PaymentFinishedRecord {

    private long orderId;
    private Integer amount;
}
