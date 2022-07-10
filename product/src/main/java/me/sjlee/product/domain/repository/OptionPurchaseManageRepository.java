package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesOption;

public interface OptionPurchaseManageRepository {
    boolean increasePurchaseCount(SalesOption salesOption, int purchaseCount);
    boolean decreasePurchaseCount(SalesOption salesOption, int purchaseCount);
    void initPurchaseCount(SalesOption salesOption);
    int getCurrentPurchaseCount(SalesOption salesOption);
}
