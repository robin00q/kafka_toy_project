package me.sjlee.order.domain;

import me.sjlee.order.domain.models.Address;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.models.OrderLine;
import me.sjlee.order.domain.models.Orderer;
import me.sjlee.order.domain.models.Receiver;
import me.sjlee.order.domain.models.ShippingInfo;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void createOrder() {
        // given : none
        Orderer orderer = new Orderer(UUID.randomUUID().toString(), "이석준");

        OrderLine orderLine1 = new OrderLine(UUID.randomUUID().toString(), 10000, 10);
        OrderLine orderLine2 = new OrderLine(UUID.randomUUID().toString(), 20000, 10);
        List<OrderLine> orderLines = Arrays.asList(orderLine1, orderLine2);

        Receiver receiver = new Receiver("이석준", "010-1234-1234");
        Address address = new Address("경기도 용인시", "101동 105호", "12345");

        // when
        Order order = new Order(UUID.randomUUID().toString(), orderer, orderLines, new ShippingInfo(address, receiver));

        // then
        assertEquals(orderLine1.getAmounts().sum(orderLine2.getAmounts()), order.getTotalAmounts());
    }
}