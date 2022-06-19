package me.sjlee.payment.application.client;

import me.sjlee.payment.domain.models.Money;

public interface OrderClient {

    boolean isValidOrder(Long orderId, Money money);
}
