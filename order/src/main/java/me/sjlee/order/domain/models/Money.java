package me.sjlee.order.domain.models;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * immutable money class
 */
@EqualsAndHashCode
@ToString
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

    public Money sum(Money money) {
        return new Money(money.value + value);
    }
}
