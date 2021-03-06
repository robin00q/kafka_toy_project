package me.sjlee.payment.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import me.sjlee.payment.common.Events;
import me.sjlee.payment.domain.event.PaymentFinishedEvent;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@Table(name = "payment")
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "amount")
    private Money amount;

    @Column(name = "payedBy")
    @Enumerated(EnumType.STRING)
    private PayedBy payedBy;

    protected Payment() {
    }

    public Payment(Long orderId, Money amount, PayedBy payedBy) {
        this.orderId = orderId;
        this.amount = amount;
        this.payedBy = payedBy;
    }

    public void finishPayment() {
        Events.raise(new PaymentFinishedEvent(orderId, amount));
    }
}
