package me.sjlee.product.infra.out.persistence.sales_product.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.SalesProductLoadRepository;
import me.sjlee.product.infra.out.persistence.sales_product.dto.SalesProductDataModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SalesProductLoadRepositoryImpl implements SalesProductLoadRepository {

    private final JpaSalesProductRepository productLoadRepository;

    @Override
    public Optional<SalesProduct> findById(Long salesProductId) {
        return productLoadRepository.findById(salesProductId)
                .map(SalesProductDataModel::toEntity);
    }

    @Override
    public boolean hasProduct(long productId, long optionId) {
        return productLoadRepository.findById(productId)
                .map(product -> product.getSalesOptions()
                .stream()
                .anyMatch(e -> optionId == e.getId()))
                .orElse(false);
    }
}
