package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.product.application.client.order.OrderClient;
import me.sjlee.product.application.client.order.OrderDetail;
import me.sjlee.product.domain.models.PurchaseRecordStatus;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionPurchaseRecord;
import me.sjlee.product.domain.repository.PurchaseManageRepository;
import me.sjlee.product.domain.repository.SalesOptionLoadRepository;
import me.sjlee.product.domain.repository.SalesOptionSaveRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockManageService {

    private final SalesOptionLoadRepository salesOptionLoadRepository;
    private final SalesOptionSaveRepository salesOptionSaveRepository;
    private final PurchaseManageRepository purchaseManageRepository;
    private final OrderClient orderClient;

    @Transactional
    public void increaseStock(long orderId) {
        OrderDetail orderDetail = orderClient.getOrderDetail(orderId);

        List<SalesOptionPurchaseRecord> records = toPurchaseRecords(orderDetail);

        boolean stockNotEnough = increaseStock(records);
        if (stockNotEnough) {
            decreaseStock(records);
        }
    }

    private List<SalesOptionPurchaseRecord> toPurchaseRecords(OrderDetail order) {
        List<SalesOptionPurchaseRecord> purchaseRecords = new ArrayList<>();

        for (OrderDetail.OrderLineDetail orderLine : order.getOrderLines()) {
            SalesOption salesOption = salesOptionLoadRepository.findOption(
                    orderLine.getProductId(), orderLine.getOptionId())
                            .orElseThrow(() -> new IllegalStateException("존재하지 않는 옵션입니다."));

            if (salesOption.isSoldOut()) {
                throw new IllegalStateException("옵션의 재고가 부족합니다.");
            }

            purchaseRecords.add(
                    toPurchaseRecord(orderLine, order.getUserId(), order.getOrderId(), salesOption.getTotalStock())
            );
        }

        return purchaseRecords;
    }

    private SalesOptionPurchaseRecord toPurchaseRecord(OrderDetail.OrderLineDetail orderLine,
                                                       long userId,
                                                       long orderId,
                                                       int totalStock) {
        return SalesOptionPurchaseRecord.builder()
                .productId(orderLine.getProductId())
                .optionId(orderLine.getOptionId())
                .quantity(orderLine.getQuantity())
                .totalStock(totalStock)
                .status(PurchaseRecordStatus.INCREASE)
                .userId(userId)
                .orderId(orderId)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private boolean increaseStock(List<SalesOptionPurchaseRecord> records) {
        boolean stockNotEnough = false;

        for (SalesOptionPurchaseRecord record : records) {
            if (!purchaseManageRepository.increaseStock(record)) {
                log.info("재고 소진됨, productId : {}, optionId : {}", record.getProductId(), record.getOptionId());
                stockNotEnough = true;
                salesOptionSaveRepository.updateSoldOut(record.getOptionId());
            }
        }

        return stockNotEnough;
    }

    private void decreaseStock(List<SalesOptionPurchaseRecord> records) {
        records.forEach(purchaseManageRepository::decreaseStock);
    }

    public long getRemainStock(long productId, long optionId) {
        SalesOption salesOption = salesOptionLoadRepository.findOption(productId, optionId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 상품입니다."));

        return salesOption.getTotalStock() - purchaseManageRepository.getCurrentPurchaseCount(productId, optionId);
    }
}
