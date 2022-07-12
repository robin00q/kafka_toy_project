package me.sjlee.product.infra.in.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.service.ProductRegisterService;
import me.sjlee.product.infra.in.controller.dto.ProductRegisterRequest;
import me.sjlee.product.infra.in.controller.dto.ProductRegisterResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductRegisterController {

    private final ProductRegisterService productRegisterService;

    @PostMapping("/register/product")
    public ProductRegisterResponse registerProduct(@RequestBody ProductRegisterRequest request) {
        return new ProductRegisterResponse(productRegisterService.register(request));
    }


}
