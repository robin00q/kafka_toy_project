package me.sjlee.product.domain.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

@Getter
@EqualsAndHashCode(of = "id")
public class SalesProduct {

    private Long id;

    private String name;

    private Long sellerId;

    private List<SalesOption> salesOptions;

    @Builder
    public SalesProduct(Long id, String name, Long sellerId, List<SalesOption> salesOptions) {
        this.id = id;
        this.name = name;
        this.sellerId = sellerId;
        this.salesOptions = salesOptions;
    }

    public void addSalesOption(List<SalesOption> options) {
        salesOptions.addAll(options);
    }
}
