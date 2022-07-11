package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesOption;

public interface OptionPurchaseHistoryRepository {
    void recordIncrease(SalesOption salesOption, int purchaseCount, long orderId, long userId);
    void recordDecrease(SalesOption salesOption, int purchaseCount, long orderId, long userId);
    long getPurchaseCount(SalesOption salesOption);
}
