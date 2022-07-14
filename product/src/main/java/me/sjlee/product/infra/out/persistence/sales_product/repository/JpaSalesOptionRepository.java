package me.sjlee.product.infra.out.persistence.sales_product.repository;

import me.sjlee.product.infra.out.persistence.sales_product.dto.SalesOptionDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaSalesOptionRepository extends JpaRepository<SalesOptionDataModel, Long> {

    Optional<SalesOptionDataModel> findBySalesProductIdAndId(long productId, long optionId);
}
