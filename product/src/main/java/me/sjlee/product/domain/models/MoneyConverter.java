package me.sjlee.product.domain.models;

import javax.persistence.AttributeConverter;

public class MoneyConverter implements AttributeConverter<Money, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Money money) {
        return money == null ? null : money.getValue();
    }

    @Override
    public Money convertToEntityAttribute(Integer value) {
        return value == null ? null : new Money(value);
    }
}

