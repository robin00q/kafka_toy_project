package me.sjlee.product.infra.in.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.service.ProductPurchaseService;
import me.sjlee.product.infra.in.controller.dto.ProductPurchaseRequest;
import me.sjlee.product.infra.in.controller.dto.ProductPurchaseResponse;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductPurchaseController {

    private final ProductPurchaseService productPurchaseService;

    public ProductPurchaseResponse purchase(@Valid ProductPurchaseRequest request) {
        return productPurchaseService.purchase(request);
    }
}
