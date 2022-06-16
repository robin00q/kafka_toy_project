package me.sjlee.order.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Orderer {

    private final String uuid;
    private final String name;

    public Orderer(String uuid, String name) {
        validate(uuid, name);
        this.uuid = uuid;
        this.name = name;
    }

    private void validate(String uuid, String name) {
        if (uuid == null || name == null) {
            throw new IllegalStateException("[Orderer] 주문자는 비어있을 수 없습니다.");
        }
    }
}
