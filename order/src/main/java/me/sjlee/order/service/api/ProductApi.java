package me.sjlee.order.service.api;

import me.sjlee.order.service.dto.ProductValidateDto;

import java.util.List;

public interface ProductApi {

    boolean canPurchase(List<ProductValidateDto> optionId);
}
