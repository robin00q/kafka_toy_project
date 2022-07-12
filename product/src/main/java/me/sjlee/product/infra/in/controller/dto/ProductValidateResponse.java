package me.sjlee.product.infra.in.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductValidateResponse {

    boolean valid;

    public static ProductValidateResponse valid() {
        return ProductValidateResponse.builder()
                .valid(true)
                .build();
    }

    public static ProductValidateResponse invalid() {
        return ProductValidateResponse.builder()
                .valid(false)
                .build();
    }
}
