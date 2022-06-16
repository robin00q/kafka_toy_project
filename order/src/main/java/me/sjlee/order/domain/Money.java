package me.sjlee.order.domain;

import lombok.EqualsAndHashCode;

/**
 * immutable money class
 */
@EqualsAndHashCode
public class Money {

    private static final int MIN_MONEY = 0;

    private final int value;

    public Money(int value) {
        if (value < MIN_MONEY) {
            throw new IllegalStateException("돈은 0보다 작을 수 없습니다.");
        }
        this.value = value;
    }

    public Money multiply(int quantity) {
        return new Money(value * quantity);
    }
}
