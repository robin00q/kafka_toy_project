package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesOptionPurchaseRecord;

public interface PurchaseManageRepository {
    boolean increaseStock(SalesOptionPurchaseRecord record);
    boolean decreaseStock(SalesOptionPurchaseRecord record);
    long getCurrentPurchaseCount(long productId, long optionId);
    void initPurchaseCount(long productId, long optionId);
}
