package me.sjlee.product.infra.in.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
@Builder
public class ProductPurchaseRequest {

    @Min(1)
    private int purchaseCount;
    @Min(1)
    private long userId;
    @Min(1)
    private long salesProductId;
    @Min(1)
    private long salesOptionId;
}
