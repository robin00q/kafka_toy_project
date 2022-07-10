package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesOption;

public interface ProductStockManageRepository {
    boolean decreaseStock(SalesOption salesOption, Integer purchaseCount);
}
