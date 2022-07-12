package me.sjlee.order.infra.out.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.sjlee.order.service.dto.ProductValidateDto;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductValidateRequest {

    private List<ProductValidateDto> products;
}
