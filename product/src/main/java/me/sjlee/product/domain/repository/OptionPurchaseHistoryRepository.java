package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesOption;

public interface OptionPurchaseHistoryRepository {
    void record(SalesOption salesOption, Integer purchaseCount);
}
