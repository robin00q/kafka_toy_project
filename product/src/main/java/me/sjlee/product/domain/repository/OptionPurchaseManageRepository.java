package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesOption;

public interface OptionPurchaseManageRepository {
    boolean increasePurchaseCount(SalesOption salesOption, int purchaseCount, long orderId, long userId);
    boolean decreasePurchaseCount(SalesOption salesOption, int purchaseCount, long orderId, long userId);
    void initPurchaseCount(SalesOption salesOption);
    long getCurrentPurchaseCount(SalesOption salesOption);
    boolean hasEnoughStock(SalesOption salesOption, int purchaseCount);
}
