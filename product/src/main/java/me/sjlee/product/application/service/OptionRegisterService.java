package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.SalesProductLoadRepository;
import me.sjlee.product.domain.repository.SalesProductSaveRepository;
import me.sjlee.product.infra.in.controller.dto.OptionRegisterRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OptionRegisterService {

    private final SalesProductLoadRepository salesProductLoadRepository;
    private final SalesProductSaveRepository salesProductSaveRepository;

    public SalesProduct registerOption(OptionRegisterRequest request) {
        SalesProduct salesProduct = salesProductLoadRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 상품입니다."));

        salesProduct.addSalesOption(getOptions(request));

        return salesProductSaveRepository.record(salesProduct);
    }

    private List<SalesOption> getOptions(OptionRegisterRequest request) {
        return request.getOptions().stream()
                .map(OptionRegisterRequest.OptionRequest::toOption)
                .collect(Collectors.toList());
    }
}
