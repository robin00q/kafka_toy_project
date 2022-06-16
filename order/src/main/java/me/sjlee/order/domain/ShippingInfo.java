package me.sjlee.order.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class ShippingInfo {

    private final Address address;
    private final Receiver receiver;

    public ShippingInfo(Address address, Receiver receiver) {
        this.address = address;
        this.receiver = receiver;
    }
}
