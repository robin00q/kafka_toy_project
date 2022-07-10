package me.sjlee.product.infra.out.persistence.sales_product.repository;

import me.sjlee.product.infra.out.persistence.sales_product.dto.SalesProductDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSalesProductRepository extends JpaRepository<SalesProductDataModel, Long> {
}
