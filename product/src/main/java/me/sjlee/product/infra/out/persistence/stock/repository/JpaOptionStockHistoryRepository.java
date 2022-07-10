package me.sjlee.product.infra.out.persistence.stock.repository;

import me.sjlee.product.infra.out.persistence.stock.dto.OptionStockHistoryDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOptionStockHistoryRepository extends JpaRepository<OptionStockHistoryDataModel, Long> {
}
