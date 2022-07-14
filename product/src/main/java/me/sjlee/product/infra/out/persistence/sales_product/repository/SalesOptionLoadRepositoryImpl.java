package me.sjlee.product.infra.out.persistence.sales_product.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.repository.SalesOptionLoadRepository;
import me.sjlee.product.infra.out.persistence.sales_product.dto.SalesOptionDataModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SalesOptionLoadRepositoryImpl implements SalesOptionLoadRepository {

    private final JpaSalesOptionRepository repository;

    @Override
    public Optional<SalesOption> findOption(long productId, long optionId) {
        return repository.findBySalesProductIdAndId(productId, optionId)
                .map(SalesOptionDataModel::toEntity);
    }
}
