package me.sjlee.product.infra.out.persistence.stock.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(indexes = {
    @Index(name = "sales_option_id_idx", columnList = "sales_option_id")
})
public class OptionStockHistoryDataModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_option_stock_history_id")
    private Long id;

    @Column(name = "sales_option_id", nullable = false)
    private Long salesOptionId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public OptionStockHistoryDataModel(Long salesOptionId, Long userId, Integer quantity, LocalDateTime createdAt) {
        this.salesOptionId = salesOptionId;
        this.userId = userId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public static OptionStockHistoryDataModel increase(SalesOption option, int purchaseCount, long userId) {
        return OptionStockHistoryDataModel.builder()
                .salesOptionId(option.getId())
                .userId(userId)
                .quantity(purchaseCount)
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static OptionStockHistoryDataModel decrease(SalesOption option, int purchaseCount, long userId) {
        return OptionStockHistoryDataModel.builder()
                .salesOptionId(option.getId())
                .userId(userId)
                .quantity(purchaseCount * -1)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
