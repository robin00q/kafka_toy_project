package me.sjlee.product.infra.in.controller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductValidateRequest {

    private List<PurchasingProductRequest> products;

    @Getter
    public static class PurchasingProductRequest {
        private long productId;
        private long optionId;
        private int quantity;
    }
}
