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

    // TODO : 트랜잭션이 보장되는지 테스트해야함
    @Transactional
    public void purchase(Integer purchaseCount, Long salesProductId, Long salesOptionId) {
        SalesProduct salesProduct = salesProductLoadRepository.findById(salesProductId)
                .orElseThrow(() -> new IllegalArgumentException("주문아이디가 비어있습니다."));

        SalesOption salesOption = salesProduct.getSalesOptions().stream()
                .filter(option -> Objects.equals(option.getId(), salesOptionId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("주문에 대한 옵션이 존재하지 않습니다."));

        try {
            // 이 부분은 도메인 서비스로 가야할까?
            increasePurchaseCount(salesOption, purchaseCount);
        } catch (StockNotEnoughException e) {
            // 이 부분은 도메인 서비스로 가야할까?
            salesOption.soldOut();
            salesProductSaveRepository.record(salesProduct);
            decreasePurchaseCount(salesOption, purchaseCount);
        }
    }


    private void increasePurchaseCount(SalesOption salesOption, Integer purchaseCount) {
        if (!optionPurchaseManageRepository.increasePurchaseCount(salesOption, purchaseCount)) {
            throw new StockNotEnoughException("재고가 부족합니다.");
        }
        // TODO : History 객체를 전달하도록
        optionPurchaseHistoryRepository.record(salesOption, purchaseCount);
    }

    private void decreasePurchaseCount(SalesOption salesOption, Integer purchaseCount) {
        optionPurchaseManageRepository.decreasePurchaseCount(salesOption, purchaseCount);
    }
}
