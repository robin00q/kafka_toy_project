package me.sjlee.product.infra.in.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.service.ProductPurchaseService;
import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.infra.in.controller.dto.ProductPurchaseResponse;
import me.sjlee.product.infra.in.controller.dto.PurchaseRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductPurchaseController {

    private final ProductPurchaseService productPurchaseService;

    @PostMapping("/purchase")
    public ProductPurchaseResponse purchase(@Valid PurchaseRequest request) {
        try {
            productPurchaseService.increaseStock(request);
            return ProductPurchaseResponse.success();
        } catch (StockNotEnoughException e) {
            return ProductPurchaseResponse.fail();
        }
    }
}
