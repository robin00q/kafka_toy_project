package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.OptionPurchaseHistoryRepository;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import me.sjlee.product.domain.repository.SalesProductLoadRepository;
import me.sjlee.product.domain.repository.SalesProductSaveRepository;
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
    private final OptionPurchaseHistoryRepository optionPurchaseHistoryRepository;

    @Transactional
    public void purchase(Integer purchaseCount, Long salesProductId, Long salesOptionId) {
        SalesProduct salesProduct = salesProductLoadRepository.findById(salesProductId)
                .orElseThrow(() -> new IllegalArgumentException("주문아이디가 비어있습니다."));

        SalesOption salesOption = salesProduct.getSalesOptions().stream()
                .filter(option -> Objects.equals(option.getId(), salesOptionId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("주문에 대한 옵션이 존재하지 않습니다."));

        try {
            increasePurchaseCount(salesOption, purchaseCount);
        } catch (StockNotEnoughException e) {
            salesOption.soldOut();
            salesProductSaveRepository.record(salesProduct);
            decreasePurchaseCount(salesOption, purchaseCount);
        }
    }


    private void increasePurchaseCount(SalesOption salesOption, Integer purchaseCount) {
        if (!optionPurchaseManageRepository.increasePurchaseCount(salesOption, purchaseCount)) {
            throw new StockNotEnoughException("재고가 부족합니다.");
        }
        optionPurchaseHistoryRepository.record(salesOption, purchaseCount);
    }

    private void decreasePurchaseCount(SalesOption salesOption, Integer purchaseCount) {
        optionPurchaseManageRepository.decreasePurchaseCount(salesOption, purchaseCount);
    }
}
