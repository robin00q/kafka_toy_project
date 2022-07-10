package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesProduct;

public interface SalesProductSaveRepository {

    SalesProduct record(SalesProduct salesProduct);
}
