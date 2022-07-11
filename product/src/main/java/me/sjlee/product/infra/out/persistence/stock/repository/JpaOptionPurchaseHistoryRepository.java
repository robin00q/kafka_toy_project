package me.sjlee.product.infra.out.persistence.stock.repository;

import me.sjlee.product.infra.out.persistence.stock.dto.OptionPurchaseHistoryDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaOptionPurchaseHistoryRepository extends JpaRepository<OptionPurchaseHistoryDataModel, Long> {

    @Query("select sum(his.quantity) " +
            "from OptionPurchaseHistoryDataModel his " +
            "where his.salesOptionId = ?1 " +
            "group by his.salesOptionId")
    long countBySalesOptionId(Long salesOptionId);
}
