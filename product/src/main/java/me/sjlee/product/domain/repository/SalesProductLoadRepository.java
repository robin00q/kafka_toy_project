package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesProduct;

import java.util.Optional;

public interface SalesProductLoadRepository {

    Optional<SalesProduct> findById(Long salesProductId);
}
