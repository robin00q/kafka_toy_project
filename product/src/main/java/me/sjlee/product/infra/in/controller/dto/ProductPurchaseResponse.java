package me.sjlee.product.infra.in.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductPurchaseResponse {

    private boolean success;

    public static ProductPurchaseResponse success() {
        return ProductPurchaseResponse.builder()
                .success(true)
                .build();
    }

    public static ProductPurchaseResponse fail() {
        return ProductPurchaseResponse.builder()
                .success(false)
                .build();
    }
}
