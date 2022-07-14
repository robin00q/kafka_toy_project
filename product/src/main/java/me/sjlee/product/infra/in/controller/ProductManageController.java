package me.sjlee.product.infra.in.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.service.ProoductManageService;
import me.sjlee.product.infra.in.controller.dto.ProductRegisterRequest;
import me.sjlee.product.infra.in.controller.dto.ProductRegisterResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductManageController {

    private final ProoductManageService prooductManageService;

    @PostMapping("/register/product")
    public ProductRegisterResponse registerProduct(@RequestBody ProductRegisterRequest request) {
        return new ProductRegisterResponse(prooductManageService.register(request));
    }

}
