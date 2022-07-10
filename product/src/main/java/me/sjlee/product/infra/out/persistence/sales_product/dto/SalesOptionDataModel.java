package me.sjlee.product.infra.out.persistence.sales_product.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sjlee.product.domain.models.Money;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionStatus;
import me.sjlee.product.infra.out.persistence.sales_product.dto.converter.MoneyConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesOptionDataModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_option_id")
    private Long id;

    @Column(name = "sales_product_id")
    private Long salesProductId;

    @Column(name = "sales_option_name", nullable = false)
    private String name;

    @Column(name = "total_stock", nullable = false)
    private Integer totalStock;

    @Convert(converter = MoneyConverter.class)
    @Column(name = "money", nullable = false)
    private Money price;

    @Column(name = "discount_rate", nullable = false)
    private Integer discountRate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "status", nullable = false)
    private SalesOptionStatus status;

    @Builder
    public SalesOptionDataModel(Long id, Long salesProductId, String name, Integer totalStock, Money price, Integer discountRate, SalesOptionStatus status) {
        this.id = id;
        this.salesProductId = salesProductId;
        this.name = name;
        this.totalStock = totalStock;
        this.price = price;
        this.discountRate = discountRate;
        this.status = status;
    }

    public static SalesOptionDataModel from(SalesOption salesOption, Long salesProductId) {
        return SalesOptionDataModel.builder()
                .id(salesOption.getId())
                .salesProductId(salesProductId)
                .name(salesOption.getName())
                .totalStock(salesOption.getTotalStock())
                .price(salesOption.getPrice())
                .discountRate(salesOption.getDiscountRate())
                .status(salesOption.getStatus())
                .build();
    }

    public SalesOption toEntity() {
        return SalesOption.builder()
                .id(id)
                .name(name)
                .totalStock(totalStock)
                .price(price)
                .discountRate(discountRate)
                .status(status)
                .build();
    }
}
