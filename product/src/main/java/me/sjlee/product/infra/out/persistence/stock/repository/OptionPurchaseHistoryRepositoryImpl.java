package me.sjlee.product.infra.out.persistence.stock.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.repository.OptionPurchaseHistoryRepository;
import me.sjlee.product.infra.out.persistence.stock.dto.OptionPurchaseHistoryDataModel;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OptionPurchaseHistoryRepositoryImpl implements OptionPurchaseHistoryRepository {

    private final JpaOptionPurchaseHistoryRepository jpaOptionPurchaseHistoryRepository;

    @Override
    public void recordIncrease(SalesOption salesOption, int purchaseCount, long orderId, long userId) {
        jpaOptionPurchaseHistoryRepository.save(OptionPurchaseHistoryDataModel.increase(salesOption, purchaseCount, orderId, userId));
    }

    @Override
    public void recordDecrease(SalesOption salesOption, int purchaseCount, long orderId, long userId) {
        jpaOptionPurchaseHistoryRepository.save(OptionPurchaseHistoryDataModel.decrease(salesOption, purchaseCount, orderId, userId));
    }

    @Override
    public long getPurchaseCount(SalesOption salesOption) {
        return jpaOptionPurchaseHistoryRepository.countBySalesOptionId(salesOption.getId());
    }
}
