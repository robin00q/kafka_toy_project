package me.sjlee.order.infra.in.event;

import lombok.Getter;

@Getter
public class PaymentFinishedRecord {

    private long orderId;
    private Integer amount;
}
