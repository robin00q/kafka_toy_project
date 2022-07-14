package me.sjlee.product.infra.in.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.service.ProductPurchaseService;
import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.infra.in.controller.dto.IncreaseStockRequest;
import me.sjlee.product.infra.in.controller.dto.ProductPurchaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class ProductPurchaseController {

    private final ProductPurchaseService productPurchaseService;

    @PostMapping("/increase/stock")
    public ProductPurchaseResponse increaseStock(@Valid IncreaseStockRequest request) {
        try {
            productPurchaseService.increaseStock(request);
            return ProductPurchaseResponse.success();
        } catch (StockNotEnoughException e) {
            return ProductPurchaseResponse.fail();
        }
    }
}
