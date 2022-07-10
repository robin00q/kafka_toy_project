package me.sjlee.product.infra.out.persistence.stock.repository;

import me.sjlee.product.infra.out.persistence.stock.dto.OptionStockHistoryDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaOptionStockHistoryRepository extends JpaRepository<OptionStockHistoryDataModel, Long> {

    @Query("select sum(his.quantity) " +
            "from OptionStockHistoryDataModel his " +
            "where his.salesOptionId = ?1 " +
            "group by his.salesOptionId")
    long countBySalesOptionId(Long salesOptionId);
}
