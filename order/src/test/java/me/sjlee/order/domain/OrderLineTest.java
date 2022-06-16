package me.sjlee.order.domain;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderLineTest {

    @Test
    void createOrderLine() {
        // given
        String productId = UUID.randomUUID().toString();
        int money = 1000;
        int quantity = 10;

        // when
        OrderLine orderLine = new OrderLine(productId, money, quantity);

        // then
        assertEquals(new Money(money * quantity), orderLine.getAmounts());
    }

}