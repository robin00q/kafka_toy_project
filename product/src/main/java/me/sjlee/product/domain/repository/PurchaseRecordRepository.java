package me.sjlee.product.domain.repository;

import me.sjlee.product.infra.out.persistence.stock.dto.OptionPurchaseHistoryDataModel;

public interface PurchaseRecordRepository {

    long getPurchaseCount(long productId, long optionId);
    OptionPurchaseHistoryDataModel save(OptionPurchaseHistoryDataModel optionPurchaseHistory);
}
