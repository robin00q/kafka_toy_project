package me.sjlee.product.infra.out.persistence.sales_product.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.repository.SalesOptionSaveRepository;
import me.sjlee.product.infra.out.persistence.sales_product.dto.SalesOptionDataModel;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class SalesOptionSaveRepositoryImpl implements SalesOptionSaveRepository {

    private final JpaSalesOptionRepository repository;

    @Override
    @Transactional
    public void updateSoldOut(long optionId) {
        SalesOption option = repository.findById(optionId)
                .map(SalesOptionDataModel::toEntity)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 옵션입니다."));

        option.soldOut();
        repository.save(SalesOptionDataModel.from(option));
    }
}
