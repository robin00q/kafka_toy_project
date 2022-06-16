package me.sjlee.order.domain;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class OrderLine {

    private final String productId;

    private final Money price;

    private final int quantity;

    private final Money amounts;

    public OrderLine(String productId, int price, int quantity) {
        validate(productId, quantity);

        this.productId = productId;
        this.price = new Money(price);
        this.quantity = quantity;
        this.amounts = new Money(price).multiply(quantity);
    }

    private void validate(String productId, int quantity) {
        if (productId == null) throw new IllegalStateException("[OrderLine] 제품 아이디는 비어있을 수 없습니다.");
        if (quantity <= 0) throw new IllegalStateException("[OrderLine] 수량은 0보다 작을 수 없습니다.");
    }

    public Money getAmounts() {
        return amounts;
    }
}
