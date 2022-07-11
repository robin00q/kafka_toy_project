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
    public void recordIncrease(SalesOption salesOption, int purchaseCount, long userId) {
        jpaOptionStockHistoryRepository.save(OptionStockHistoryDataModel.increase(salesOption, purchaseCount, userId));
    }

    @Override
    public void recordDecrease(SalesOption salesOption, int purchaseCount, long userId) {
        jpaOptionStockHistoryRepository.save(OptionStockHistoryDataModel.decrease(salesOption, purchaseCount, userId));
    }

    @Override
    public long getPurchaseCount(SalesOption salesOption) {
        return jpaOptionStockHistoryRepository.countBySalesOptionId(salesOption.getId());
    }
}
