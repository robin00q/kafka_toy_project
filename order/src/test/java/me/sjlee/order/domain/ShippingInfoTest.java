package me.sjlee.order.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.*;

class ShippingInfoTest {

    @Test
    void createShippingInfo() {
        // given
        Address address = new Address("경기도 용인시 기흥구", "101동 105호", "12345");
        Receiver receiver = new Receiver("이석준", "010-1234-1234");

        // when, then
        assertThatCode(() -> new ShippingInfo(address, receiver))
                .doesNotThrowAnyException();
    }
}