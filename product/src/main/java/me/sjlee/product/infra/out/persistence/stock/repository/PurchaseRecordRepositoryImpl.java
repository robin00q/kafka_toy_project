package me.sjlee.product.infra.out.persistence.stock.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.repository.PurchaseRecordRepository;
import me.sjlee.product.infra.out.persistence.stock.dto.OptionPurchaseHistoryDataModel;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PurchaseRecordRepositoryImpl implements PurchaseRecordRepository {

    private final JpaOptionPurchaseHistoryRepository jpaOptionPurchaseHistoryRepository;

    @Override
    public long getPurchaseCount(long productId, long optionId) {
        return jpaOptionPurchaseHistoryRepository.countBySalesOptionId(productId, optionId);
    }

    @Override
    public OptionPurchaseHistoryDataModel save(OptionPurchaseHistoryDataModel optionPurchaseHistory) {
        return jpaOptionPurchaseHistoryRepository.save(optionPurchaseHistory);
    }
}
