package me.sjlee.product.infra.out.persistence.stock.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.sjlee.product.domain.models.SalesOptionPurchaseRecord;

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
public class OptionPurchaseHistoryDataModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sales_option_stock_history_id")
    private Long id;

    @Column(name = "sales_product_id", nullable = false)
    private Long salesProductId;

    @Column(name = "sales_option_id", nullable = false)
    private Long salesOptionId;

    @Column(name = "order_id", nullable = false)
    private Long orderId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Builder
    public OptionPurchaseHistoryDataModel(Long salesProductId, Long salesOptionId, Long orderId, Long userId, Integer quantity, LocalDateTime createdAt) {
        this.salesProductId = salesProductId;
        this.salesOptionId = salesOptionId;
        this.orderId = orderId;
        this.userId = userId;
        this.quantity = quantity;
        this.createdAt = createdAt;
    }

    public static OptionPurchaseHistoryDataModel increase(SalesOptionPurchaseRecord record) {
        return OptionPurchaseHistoryDataModel.builder()
                .salesProductId(record.getProductId())
                .salesOptionId(record.getOptionId())
                .orderId(record.getOrderId())
                .userId(record.getUserId())
                .quantity(record.getQuantity())
                .createdAt(record.getCreatedAt())
                .build();
    }

    public static OptionPurchaseHistoryDataModel decrease(SalesOptionPurchaseRecord record) {
        return OptionPurchaseHistoryDataModel.builder()
                .salesProductId(record.getProductId())
                .salesOptionId(record.getOptionId())
                .orderId(record.getOrderId())
                .userId(record.getUserId())
                .quantity(record.getQuantity() * -1)
                .createdAt(record.getCreatedAt())
                .build();
    }
}
