package me.sjlee.product.application.service;

import me.sjlee.product.domain.exception.StockNotEnoughException;
import me.sjlee.product.domain.models.Money;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionStatus;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import me.sjlee.product.domain.repository.SalesProductSaveRepository;
import me.sjlee.product.infra.in.controller.dto.ProductPurchaseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class ProductPurchaseServiceTest {

    @Autowired
    private ProductPurchaseService productPurchaseService;

    @Autowired
    private SalesProductSaveRepository salesProductSaveRepository;

    @Autowired
    private OptionPurchaseManageRepository optionPurchaseManageRepository;

    private SalesProduct salesProduct;

    @BeforeEach
    void setUp() {
        // 판매상품을 한개 등록한다.
        SalesOption option = SalesOption.builder()
                .name("맛있는 석준치킨")
                .totalStock(10)
                .price(new Money(10000))
                .discountRate(10)
                .status(SalesOptionStatus.ON_SALE)
                .build();

        SalesProduct product = SalesProduct.builder()
                .name("판매상품")
                .sellerId(1L)
                .salesOptions(Collections.singletonList(option))
                .build();

        salesProduct = salesProductSaveRepository.record(product);
        optionPurchaseManageRepository.initPurchaseCount(salesProduct.getSalesOptions().get(0));
    }

    @DisplayName("구매 성공")
    @Test
    void purchase() {
        // given
        SalesOption option = salesProduct.getSalesOptions().get(0);
        int purchaseCount = option.getTotalStock();

        ProductPurchaseRequest productPurchaseRequest = ProductPurchaseRequest.builder()
                .purchaseCount(purchaseCount)
                .userId(10000)
                .salesProductId(salesProduct.getId())
                .salesOptionId(option.getId())
                .build();

        // when
        productPurchaseService.purchase(productPurchaseRequest);

        // then
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(option)).isEqualTo(purchaseCount);
    }

    @DisplayName("재고 부족으로 인한 구매 싪패")
    @Test
    void purchase_fail() {
        // given
        SalesOption option = salesProduct.getSalesOptions().get(0);
        int purchaseCount = option.getTotalStock() + 10;

        ProductPurchaseRequest productPurchaseRequest = ProductPurchaseRequest.builder()
                .purchaseCount(purchaseCount)
                .userId(10000)
                .salesProductId(salesProduct.getId())
                .salesOptionId(option.getId())
                .build();

        // when, then
        assertThatThrownBy(() -> productPurchaseService.purchase(productPurchaseRequest))
                .isInstanceOf(StockNotEnoughException.class);
    }

}