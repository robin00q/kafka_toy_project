package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.repository.SalesProductLoadRepository;
import me.sjlee.product.infra.in.controller.dto.ProductValidateRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductValidateService {

    private final SalesProductLoadRepository salesProductLoadRepository;

    public boolean isValid(ProductValidateRequest request) {
        for (ProductValidateRequest.PurchasingProductRequest product : request.getProducts()) {
            if (!salesProductLoadRepository.hasProduct(product.getProductId(), product.getOptionId())) {
                return false;
            }
        }

        return true;
    }
}
