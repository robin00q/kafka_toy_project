package me.sjlee.product.infra.out.persistence.stock.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.repository.OptionPurchaseHistoryRepository;
import me.sjlee.product.infra.out.persistence.stock.dto.OptionStockHistoryDataModel;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OptionPurchaseHistoryRepositoryImpl implements OptionPurchaseHistoryRepository {

    private final JpaOptionStockHistoryRepository jpaOptionStockHistoryRepository;

    @Override
    public void record(SalesOption salesOption, Integer purchaseCount) {
        jpaOptionStockHistoryRepository.save(OptionStockHistoryDataModel.increase(salesOption, purchaseCount));
    }
}
