package me.sjlee.order.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * root entity for order
 */
@Entity
@Getter @EqualsAndHashCode(of = "id")
@Table(name = "purchase_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    @Embedded
    private Orderer orderer;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private List<OrderLine> orderLines = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_id")
    private ShippingInfo shippingInfo;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "total_amounts")
    private Money totalAmounts;

    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    protected Order() {}

    public Order(Orderer orderer, List<OrderLine> orderLines, ShippingInfo shippingInfo) {
        this.orderer = orderer;
        this.orderLines = orderLines;
        this.shippingInfo = shippingInfo;
        this.totalAmounts = calculateTotalAmount(orderLines);
        this.status = OrderStatus.PAYMENT_WAITING;
    }

    private Money calculateTotalAmount(List<OrderLine> orderLines) {
        Money money = new Money(0);

        for (OrderLine line : orderLines) {
            money = money.sum(line.getAmounts());
        }

        return money;
    }

    public void finishPayment() {
        this.status = OrderStatus.PREPARE_SHIPPING;
    }
}
