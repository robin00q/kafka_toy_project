package me.sjlee.product.application.service;

import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.domain.models.Money;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionStatus;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import me.sjlee.product.domain.repository.SalesOptionLoadRepository;
import me.sjlee.product.domain.repository.SalesProductSaveRepository;
import me.sjlee.product.infra.in.controller.dto.PurchaseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@Transactional
class ProductPurchaseServiceTest {

    @Autowired
    private ProductPurchaseService productPurchaseService;
    @Autowired
    private SalesProductSaveRepository salesProductSaveRepository;
    @Autowired
    private OptionPurchaseManageRepository optionPurchaseManageRepository;
    @Autowired
    private SalesOptionLoadRepository optionLoadRepository;

    private SalesProduct savedProduct;

    @BeforeEach
    void setUp() {
        SalesProduct salesProduct = SalesProduct.builder()
                .name("석준치킨")
                .sellerId(1L)
                .build();

        savedProduct = salesProductSaveRepository.record(salesProduct);

        SalesOption option1 = SalesOption.builder()
                .salesProductId(savedProduct.getId())
                .name("석준치킨-10000원")
                .totalStock(10)
                .price(new Money(10000))
                .discountRate(0)
                .status(SalesOptionStatus.ON_SALE)
                .build();

        SalesOption option2 = SalesOption.builder()
                .salesProductId(savedProduct.getId())
                .name("석준치킨-20000원")
                .totalStock(10)
                .price(new Money(20000))
                .discountRate(0)
                .status(SalesOptionStatus.ON_SALE)
                .build();

        savedProduct.addSalesOption(Arrays.asList(option1, option2));
        savedProduct = salesProductSaveRepository.record(savedProduct);
    }

    @Test
    void 상품_재고_증가_성공() {
        // given
        SalesOption firstOption = savedProduct.getSalesOptions().get(0);
        SalesOption secondOption = savedProduct.getSalesOptions().get(1);

        optionPurchaseManageRepository.initPurchaseCount(savedProduct.getId(), firstOption.getId());
        optionPurchaseManageRepository.initPurchaseCount(savedProduct.getId(), secondOption.getId());

        PurchaseRequest.PurchaseOptionRequest optionRequest1 = PurchaseRequest.PurchaseOptionRequest.builder()
                .productId(savedProduct.getId())
                .optionId(firstOption.getId())
                .quantity(1)
                .build();

        PurchaseRequest.PurchaseOptionRequest optionRequest2 = PurchaseRequest.PurchaseOptionRequest.builder()
                .productId(savedProduct.getId())
                .optionId(secondOption.getId())
                .quantity(2)
                .build();

        PurchaseRequest request = PurchaseRequest.builder()
                .orderId(1L)
                .userId(1L)
                .purchaseOptionRequests(Arrays.asList(optionRequest1, optionRequest2))
                .build();

        // when
        assertDoesNotThrow(() -> productPurchaseService.increaseStock(request));

        // then
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(savedProduct.getId(), firstOption.getId()))
                .isEqualTo(optionRequest1.getQuantity());
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(savedProduct.getId(), secondOption.getId()))
                .isEqualTo(optionRequest2.getQuantity());
    }

    @Test
    void 상품_재고_증가_실패() {
        // given
        SalesOption firstOption = savedProduct.getSalesOptions().get(0);
        SalesOption secondOption = savedProduct.getSalesOptions().get(1);

        optionPurchaseManageRepository.initPurchaseCount(savedProduct.getId(), firstOption.getId());
        optionPurchaseManageRepository.initPurchaseCount(savedProduct.getId(), secondOption.getId());

        PurchaseRequest.PurchaseOptionRequest optionRequest1 = PurchaseRequest.PurchaseOptionRequest.builder()
                .productId(savedProduct.getId())
                .optionId(firstOption.getId())
                .quantity(11) // 1번 옵션은 더 많은 상품을 구매한다.
                .build();

        PurchaseRequest.PurchaseOptionRequest optionRequest2 = PurchaseRequest.PurchaseOptionRequest.builder()
                .productId(savedProduct.getId())
                .optionId(secondOption.getId())
                .quantity(2)
                .build();

        PurchaseRequest request = PurchaseRequest.builder()
                .orderId(1L)
                .userId(1L)
                .purchaseOptionRequests(Arrays.asList(optionRequest1, optionRequest2))
                .build();

        // when
        assertThatThrownBy(() -> productPurchaseService.increaseStock(request))
                .isInstanceOf(StockNotEnoughException.class);

        // then
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(savedProduct.getId(), firstOption.getId()))
                .isEqualTo(0);
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(savedProduct.getId(), secondOption.getId()))
                .isEqualTo(0);
        assertThat(optionLoadRepository.findOption(savedProduct.getId(), firstOption.getId()).get().isSoldOut()).isTrue();
        assertThat(optionLoadRepository.findOption(savedProduct.getId(), secondOption.getId()).get().isSoldOut()).isFalse();
    }
}