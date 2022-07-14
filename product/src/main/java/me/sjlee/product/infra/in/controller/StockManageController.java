package me.sjlee.product.infra.in.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.service.StockManageService;
import me.sjlee.product.infra.in.controller.dto.StockResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockManageController {

    private final StockManageService stockManageService;

    @GetMapping("/stock")
    public StockResponse getStock(@RequestParam long productId,
                                  @RequestParam long optionId) {
        return new StockResponse(productId, optionId, stockManageService.getRemainStock(productId, optionId));
    }
}
