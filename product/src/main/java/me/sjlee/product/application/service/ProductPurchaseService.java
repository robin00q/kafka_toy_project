package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.product.domain.event.StockNotEnoughEvent;
import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.ProductStockHistoryRepository;
import me.sjlee.product.domain.repository.ProductStockManageRepository;
import me.sjlee.product.domain.repository.SalesProductLoadRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductPurchaseService {

    private final SalesProductLoadRepository salesProductLoadRepository;
    private final ProductStockManageRepository productStockManageRepository;
    private final ProductStockHistoryRepository productStockHistoryRepository;
    private final ApplicationEventPublisher publisher;

    @Transactional
    public void purchase(Integer purchaseCount, Long salesProductId, Long salesOptionId) {
        SalesOption salesOption = getSalesOption(salesProductId, salesOptionId);
        try {
            decreaseStock(salesOption, purchaseCount);
        } catch (StockNotEnoughException e) {
            publisher.publishEvent(new StockNotEnoughEvent());
        }
    }

    private SalesOption getSalesOption(Long salesProductId, Long salesOptionId) {
        SalesProduct salesProduct = salesProductLoadRepository.findById(salesProductId)
                .orElseThrow(() -> new IllegalArgumentException("주문아이디가 비어있습니다."));

        return salesProduct.getSalesOptions().stream()
                .filter(option -> Objects.equals(option.getId(), salesOptionId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("주문에 대한 옵션이 존재하지 않습니다."));
    }

    private void decreaseStock(SalesOption salesOption, Integer purchaseCount) {
        if (!productStockManageRepository.decreaseStock(salesOption, purchaseCount)) {
            throw new StockNotEnoughException("재고가 부족합니다.");
        }
        productStockHistoryRepository.record(salesOption);
    }

}
