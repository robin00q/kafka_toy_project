package me.sjlee.product.infra.in.controller.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProductRegisterRequest {
    private String name;
    private Long sellerId;
}
