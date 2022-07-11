package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import me.sjlee.product.domain.repository.SalesProductLoadRepository;
import me.sjlee.product.domain.repository.SalesProductSaveRepository;
import me.sjlee.product.infra.in.controller.dto.ProductPurchaseRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductPurchaseService {

    private final SalesProductLoadRepository salesProductLoadRepository;
    private final SalesProductSaveRepository salesProductSaveRepository;
    private final OptionPurchaseManageRepository optionPurchaseManageRepository;

    @Transactional
    public void purchase(ProductPurchaseRequest request) {
        SalesProduct salesProduct = salesProductLoadRepository.findById(request.getSalesProductId())
                .orElseThrow(() -> new IllegalArgumentException("주문아이디가 비어있습니다."));

        SalesOption salesOption = salesProduct.getSalesOptions().stream()
                .filter(option -> Objects.equals(option.getId(), request.getSalesOptionId()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("주문에 대한 옵션이 존재하지 않습니다."));

        if (salesOption.isSoldOut()) {
            throw new StockNotEnoughException("재고가 부족합니다.");
        }

        try {
            // 이 부분은 도메인 서비스로 가야할까?
            increasePurchaseCount(salesOption, request.getPurchaseCount(), request.getOrderId(), request.getUserId());
        } catch (StockNotEnoughException e) {
            // 이 부분은 도메인 서비스로 가야할까?
            salesOption.soldOut();
            salesProductSaveRepository.record(salesProduct);
            decreasePurchaseCount(salesOption, request.getPurchaseCount(), request.getOrderId(), request.getUserId());
            throw e;
        }
    }

    private void increasePurchaseCount(SalesOption salesOption, int purchaseCount, long orderId, long userId) {
        if (!optionPurchaseManageRepository.increasePurchaseCount(salesOption, purchaseCount, orderId, userId)) {
            throw new StockNotEnoughException("재고가 부족합니다.");
        }
    }

    private void decreasePurchaseCount(SalesOption salesOption, int purchaseCount, long orderId, long userId) {
        optionPurchaseManageRepository.decreasePurchaseCount(salesOption, purchaseCount, orderId, userId);
    }

}
