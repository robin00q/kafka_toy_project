package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.SalesProductSaveRepository;
import me.sjlee.product.infra.in.controller.dto.ProductRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductRegisterService {

    private final SalesProductSaveRepository salesProductSaveRepository;

    public Long register(ProductRegisterRequest request) {
        SalesProduct product = SalesProduct.builder()
                .name(request.getName())
                .sellerId(request.getSellerId())
                .salesOptions(new ArrayList<>())
                .build();

        return salesProductSaveRepository.record(product).getId();
    }
}
