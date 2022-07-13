package me.sjlee.product.infra.in.controller;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.service.OptionRegisterService;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.infra.in.controller.dto.OptionRegisterRequest;
import me.sjlee.product.infra.in.controller.dto.OptionRegisterResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OptionRegisterController {

    private final OptionRegisterService optionRegisterService;

    @PostMapping("/register/option")
    public OptionRegisterResponse registerOption(@RequestBody OptionRegisterRequest request) {
        SalesProduct salesProduct = optionRegisterService.registerOption(request);

        return new OptionRegisterResponse(salesProduct.getSalesOptions().stream()
                .map(SalesOption::getId)
                .collect(Collectors.toList()));
    }
}
