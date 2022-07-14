package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.product.application.client.order.OrderClient;
import me.sjlee.product.application.client.order.OrderDetail;
import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.domain.models.PurchaseRecordStatus;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionPurchaseRecord;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import me.sjlee.product.domain.repository.SalesOptionLoadRepository;
import me.sjlee.product.domain.repository.SalesOptionSaveRepository;
import me.sjlee.product.infra.in.controller.dto.IncreaseStockRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductPurchaseService {

    private final SalesOptionLoadRepository salesOptionLoadRepository;
    private final SalesOptionSaveRepository salesOptionSaveRepository;
    private final OptionPurchaseManageRepository optionPurchaseManageRepository;
    private final OrderClient orderClient;

    @Transactional
    public void increaseStock(IncreaseStockRequest request) {
        OrderDetail orderDetail = orderClient.getOrderDetail(request.getOrderId());

        List<SalesOptionPurchaseRecord> records = toPurchaseRecords(orderDetail);

        boolean stockNotEnough = increaseStock(records);
        if (stockNotEnough) {
            decreaseStock(records);
            throw new StockNotEnoughException("재고가 부족합니다.");
        }
    }

    private List<SalesOptionPurchaseRecord> toPurchaseRecords(OrderDetail order) {
        List<SalesOptionPurchaseRecord> purchaseRecords = new ArrayList<>();

        for (OrderDetail.OrderLineDetail orderLine : order.getOrderLines()) {
            SalesOption salesOption = salesOptionLoadRepository.findOption(orderLine.getProductId(), orderLine.getOptionId())
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
            if (!optionPurchaseManageRepository.increaseStock(record)) {
                stockNotEnough = true;
                salesOptionSaveRepository.updateSoldOut(record.getOptionId());
            }
        }

        return stockNotEnough;
    }

    private void decreaseStock(List<SalesOptionPurchaseRecord> records) {
        records.forEach(optionPurchaseManageRepository::decreaseStock);
    }

}
