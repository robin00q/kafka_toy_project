package me.sjlee.product.infra.out.persistence.stock.repository;

import me.sjlee.product.domain.models.PurchaseRecordStatus;
import me.sjlee.product.domain.models.SalesOptionPurchaseRecord;
import me.sjlee.product.domain.repository.PurchaseManageRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PurchaseOptionRequestPurchaseManageRepositoryImplTest {

    @Autowired
    private PurchaseManageRepository purchaseManageRepository;

    @Test
    void 구매수량_증가() {
        // given
        int quantity = 1;
        int totalStock = 10;
        long productId = 1;
        long optionId = 1;

        purchaseManageRepository.initPurchaseCount(productId, optionId);

        SalesOptionPurchaseRecord record = SalesOptionPurchaseRecord.builder()
                .productId(productId)
                .optionId(optionId)
                .quantity(quantity)
                .totalStock(totalStock)
                .status(PurchaseRecordStatus.INCREASE)
                .userId(1L)
                .orderId(1L)
                .createdAt(LocalDateTime.now())
                .build();


        // when
        boolean result = purchaseManageRepository.increaseStock(record);

        // then
        assertThat(result).isTrue();
        assertThat(purchaseManageRepository.getCurrentPurchaseCount(record.getProductId(), record.getOptionId())).isEqualTo(quantity);
    }

    @Test
    void 구매수량_증가_수량보다_많은_경우_false_반환() {
        // given
        int quantity = 11;
        int totalStock = 10;
        long productId = 1;
        long optionId = 1;

        purchaseManageRepository.initPurchaseCount(productId, optionId);

        SalesOptionPurchaseRecord record = SalesOptionPurchaseRecord.builder()
                .productId(productId)
                .optionId(optionId)
                .quantity(quantity)
                .totalStock(totalStock)
                .status(PurchaseRecordStatus.INCREASE)
                .userId(1L)
                .orderId(1L)
                .createdAt(LocalDateTime.now())
                .build();

        // when
        boolean result = purchaseManageRepository.increaseStock(record);

        // then
        assertThat(result).isFalse();
        assertThat(purchaseManageRepository.getCurrentPurchaseCount(record.getProductId(), record.getOptionId())).isEqualTo(quantity);
    }

    @Test
    void 구매수량_감소() {
        // given
        int quantity = 1;
        int totalStock = 10;
        long productId = 1;
        long optionId = 1;

        purchaseManageRepository.initPurchaseCount(productId, optionId);

        SalesOptionPurchaseRecord record = SalesOptionPurchaseRecord.builder()
                .productId(productId)
                .optionId(optionId)
                .quantity(quantity)
                .totalStock(totalStock)
                .status(PurchaseRecordStatus.INCREASE)
                .userId(1L)
                .orderId(1L)
                .createdAt(LocalDateTime.now())
                .build();

        purchaseManageRepository.increaseStock(record);
        purchaseManageRepository.increaseStock(record);

        // when
        boolean result = purchaseManageRepository.decreaseStock(record);

        // then
        assertThat(result).isTrue();
        assertThat(purchaseManageRepository.getCurrentPurchaseCount(record.getProductId(), record.getOptionId())).isEqualTo(1);
    }


}