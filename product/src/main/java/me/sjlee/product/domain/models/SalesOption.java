package me.sjlee.product.domain.models;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@EqualsAndHashCode(of = "id")
public class SalesOption {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_option_id")
    private Long id;

    @Column(name = "sales_product_id")
    private Long salesProductId;

    @Column(name = "sales_option_name")
    private String name;

    @Column(name = "total_stock")
    private Integer totalStock;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "price")
    private Money price;

    @Column(name = "discount_rate")
    private Integer discountRate;

    @Enumerated(value = EnumType.STRING)
    private SalesOptionStatus status;
}
