package me.sjlee.order.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "order_line")
@EqualsAndHashCode
public class OrderLine {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_line_id")
    private Long id;

    @Column(name = "product_id")
    private String productId;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;

    @Column(name = "quantity")
    private int quantity;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "amounts")
    private Money amounts;

    protected OrderLine() {
    }

    public OrderLine(String productId, int price, int quantity) {
        this.productId = productId;
        this.price = new Money(price);
        this.quantity = quantity;
        this.amounts = new Money(price).multiply(quantity);
    }

    public Money getAmounts() {
        return amounts;
    }
}
