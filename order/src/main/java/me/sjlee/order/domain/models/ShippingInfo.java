package me.sjlee.order.domain.models;

import lombok.EqualsAndHashCode;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "shipping_info")
@EqualsAndHashCode
public class ShippingInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address1", column = @Column(name = "shipping_addr1")),
            @AttributeOverride(name = "address2", column = @Column(name = "shipping_addr2")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "shipping_zipcode"))
    })
    private Address address;

    @Embedded
    private Receiver receiver;

    protected ShippingInfo() {
    }

    public ShippingInfo(Address address, Receiver receiver) {
        this.address = address;
        this.receiver = receiver;
    }
}
