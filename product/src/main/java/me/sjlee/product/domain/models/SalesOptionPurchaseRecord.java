package me.sjlee.product.domain.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@EqualsAndHashCode(of = "id")
public class SalesOptionPurchaseRecord {

    private Long id;

    private long productId;
    private long optionId;
    private int quantity;
    private int totalStock;
    private PurchaseRecordStatus status;

    private long userId;
    private long orderId;

    private LocalDateTime createdAt;

    @Builder
    public SalesOptionPurchaseRecord(long productId, long optionId, int quantity, int totalStock, PurchaseRecordStatus status, long userId, long orderId, LocalDateTime createdAt) {
        this.productId = productId;
        this.optionId = optionId;
        this.quantity = quantity;
        this.totalStock = totalStock;
        this.status = status;
        this.userId = userId;
        this.orderId = orderId;
        this.createdAt = createdAt;
    }

    public void updateTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }
}
