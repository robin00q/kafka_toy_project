package me.sjlee.product.application.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.domain.models.PurchaseRecordStatus;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionPurchaseRecord;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import me.sjlee.product.domain.repository.SalesOptionLoadRepository;
import me.sjlee.product.domain.repository.SalesOptionSaveRepository;
import me.sjlee.product.infra.in.controller.dto.PurchaseRequest;
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

    @Transactional
    public void increaseStock(PurchaseRequest request) {
        List<SalesOptionPurchaseRecord> records = toPurchaseRecords(request);

        boolean stockNotEnough = increaseStock(records);
        if (stockNotEnough) {
            decreaseStock(records);
            throw new StockNotEnoughException("재고가 부족합니다.");
        }
    }

    private List<SalesOptionPurchaseRecord> toPurchaseRecords(PurchaseRequest request) {
        List<SalesOptionPurchaseRecord> purchaseRecords = new ArrayList<>();

        for (PurchaseRequest.PurchaseOptionRequest each : request.getPurchaseOptionRequests()) {
            SalesOption salesOption = salesOptionLoadRepository.findOption(each.getProductId(), each.getOptionId())
                            .orElseThrow(() -> new IllegalStateException("존재하지 않는 옵션입니다."));

            if (salesOption.isSoldOut()) {
                throw new IllegalStateException("옵션의 재고가 부족합니다.");
            }

            purchaseRecords.add(toPurchaseRecord(each, request, salesOption.getTotalStock()));
        }

        return purchaseRecords;
    }

    private SalesOptionPurchaseRecord toPurchaseRecord(PurchaseRequest.PurchaseOptionRequest optionRequest,
                                                       PurchaseRequest request,
                                                       Integer totalStock) {
        return SalesOptionPurchaseRecord.builder()
                .productId(optionRequest.getProductId())
                .optionId(optionRequest.getOptionId())
                .quantity(optionRequest.getQuantity())
                .totalStock(totalStock)
                .status(PurchaseRecordStatus.INCREASE)
                .userId(request.getUserId())
                .orderId(request.getOrderId())
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
