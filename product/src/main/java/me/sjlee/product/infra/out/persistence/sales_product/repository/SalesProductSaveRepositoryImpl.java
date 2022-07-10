package me.sjlee.product.infra.out.persistence.sales_product.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.SalesProductSaveRepository;
import me.sjlee.product.infra.out.persistence.sales_product.dto.SalesProductDataModel;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SalesProductSaveRepositoryImpl implements SalesProductSaveRepository {

    private final JpaSalesProductRepository jpaSalesProductRepository;

    @Override
    public SalesProduct record(SalesProduct salesProduct) {
        return jpaSalesProductRepository.save(SalesProductDataModel.from(salesProduct)).toEntity();
    }

}
