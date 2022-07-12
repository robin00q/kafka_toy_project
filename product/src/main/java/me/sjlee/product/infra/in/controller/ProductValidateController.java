package me.sjlee.product.infra.in.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.service.ProductValidateService;
import me.sjlee.product.infra.in.controller.dto.ProductValidateRequest;
import me.sjlee.product.infra.in.controller.dto.ProductValidateResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductValidateController {

    private final ProductValidateService productValidateService;

    @PostMapping("/product/validate")
    public ProductValidateResponse validateProduct(@RequestBody ProductValidateRequest request) {
        if (productValidateService.isValid(request)) {
            return ProductValidateResponse.valid();
        }
        return ProductValidateResponse.invalid();
    }
}
