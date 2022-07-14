package me.sjlee.product.infra.in.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.Min;

@Getter
@AllArgsConstructor
public class IncreaseStockRequest {
    @Min(1)
    private long orderId;
}
