package me.sjlee.order.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * root entity for order
 */
@EqualsAndHashCode(of = "uuid")
public class Order {

    private String uuid;

    private Orderer orderer;

    private List<OrderLine> orderLines;

    private ShippingInfo shippingInfo;

    @Getter
    private Money totalAmounts;

    public Order(String uuid, Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        this.uuid = uuid;
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.shippingInfo = shippingInfo;
        this.totalAmounts = calculateTotalAmount(orderLines);
    }

    private Money calculateTotalAmount(List<OrderLine> orderLines) {
        Money money = new Money(0);

        for (OrderLine line : orderLines) {
            money = money.sum(line.getAmounts());
        }

        return money;
    }
}
