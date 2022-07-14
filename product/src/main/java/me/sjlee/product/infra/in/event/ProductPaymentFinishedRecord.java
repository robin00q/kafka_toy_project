package me.sjlee.product.infra.in.event;

import lombok.Getter;

@Getter
public class ProductPaymentFinishedRecord {

    private long orderId;
    private Integer amount;
}
