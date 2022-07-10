package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.ProductStockHistory;
import me.sjlee.product.domain.models.SalesOption;

public interface ProductStockHistoryRepository {
    ProductStockHistory record(SalesOption salesOption);
}
