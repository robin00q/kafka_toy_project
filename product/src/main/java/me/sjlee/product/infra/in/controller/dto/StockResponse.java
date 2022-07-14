package me.sjlee.product.infra.in.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StockResponse {
    private long productId;
    private long optionId;
    private long remainStock;
}
