package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesOption;

public interface OptionPurchaseHistoryRepository {
    void recordIncrease(SalesOption salesOption, Integer purchaseCount);
    void recordDecrease(SalesOption salesOption, Integer purchaseCount);
    long getPurchaseCount(SalesOption salesOption);
}
