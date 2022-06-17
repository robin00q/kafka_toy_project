package me.sjlee.order.domain.models;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Orderer {

    private final String userId;
    private final String name;

    public Orderer(String userId, String name) {
        validate(userId, name);
        this.userId = userId;
        this.name = name;
    }

    private void validate(String uuid, String name) {
        if (uuid == null || name == null) {
            throw new IllegalStateException("[Orderer] 주문자는 비어있을 수 없습니다.");
        }
    }
}
