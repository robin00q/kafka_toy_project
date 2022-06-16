package me.sjlee.order.domain.models;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Receiver {

    private final String name;
    private final String phoneNumber;

    public Receiver(String name, String phoneNumber) {
        validate(name, phoneNumber);
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    private void validate(String name, String phoneNumber) {
        if (name == null || phoneNumber == null) {
            throw new IllegalStateException("[Receiver] 주문의 수신자는 비어있을 수 없습니다");
        }
    }
}
