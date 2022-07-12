package me.sjlee.order.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductValidateDto {

    private long productId;
    private long optionId;
    private int quantity;
}
