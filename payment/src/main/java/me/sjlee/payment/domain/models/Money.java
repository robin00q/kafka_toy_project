package me.sjlee.payment.domain.models;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * immutable money class
 */
@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Money {

    private static final int MIN_MONEY = 0;

    private int value;

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
