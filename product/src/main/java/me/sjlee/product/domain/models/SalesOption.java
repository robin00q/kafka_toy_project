package me.sjlee.product.domain.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(of = "id")
public class SalesOption {

    private Long id;

    private Long salesProductId;

    private String name;

    private Integer totalStock;

    private Money price;

    private Integer discountRate;

    private SalesOptionStatus status;

    @Builder
    public SalesOption(Long id, Long salesProductId, String name, Integer totalStock, Money price, Integer discountRate, SalesOptionStatus status) {
        this.id = id;
        this.salesProductId = salesProductId;
        this.name = name;
        this.totalStock = totalStock;
        this.price = price;
        this.discountRate = discountRate;
        this.status = status;
    }

    public void soldOut() {
        this.status = SalesOptionStatus.SOLD_OUT;
    }

    public boolean isSoldOut() {
        return status == SalesOptionStatus.SOLD_OUT;
    }
}
