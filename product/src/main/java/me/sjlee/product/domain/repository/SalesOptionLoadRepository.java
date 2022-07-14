package me.sjlee.product.domain.repository;

import me.sjlee.product.domain.models.SalesOption;

import java.util.Optional;

public interface SalesOptionLoadRepository {

    Optional<SalesOption> findOption(long productId, long optionId);
}
