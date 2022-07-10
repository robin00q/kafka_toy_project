package me.sjlee.product.infra.out.persistence.stock.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OptionStockHistoryDataModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_option_stock_history_id")
    private Long id;

    @Column(name = "sales_option_id", nullable = false)
    private Long salesOptionId;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "purchase_status", nullable = false)
    private OptionStockPurchaseStatus status;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public OptionStockHistoryDataModel(Long id, Long salesOptionId, OptionStockPurchaseStatus status, Integer quantity, LocalDateTime createdAt) {
        this.id = id;
        this.salesOptionId = salesOptionId;
        this.status = status;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public static OptionStockHistoryDataModel increase(SalesOption option, Integer purchaseCount) {
        return OptionStockHistoryDataModel.builder()
                .salesOptionId(option.getId())
                .status(OptionStockPurchaseStatus.INCREASE)
                .quantity(purchaseCount)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
