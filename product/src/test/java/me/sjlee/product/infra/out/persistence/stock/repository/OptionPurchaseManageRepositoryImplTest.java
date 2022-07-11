package me.sjlee.product.infra.out.persistence.stock.repository;

import me.sjlee.product.domain.models.Money;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionStatus;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Disabled
class OptionPurchaseManageRepositoryImplTest {

    @Autowired
    private OptionPurchaseManageRepository optionPurchaseManageRepository;

    private SalesOption salesOption;

    @BeforeEach
    void setUp() {
        salesOption = SalesOption.builder()
                .id(1L)
                .name("석준치킨")
                .totalStock(100)
                .price(new Money(10000))
                .discountRate(10)
                .status(SalesOptionStatus.ON_SALE)
                .build();

        optionPurchaseManageRepository.initPurchaseCount(salesOption);
    }

    @Test
    void 상품_구매_시_구매수량_증가() {
        // given
        int purchaseCount = 1;
        long userId = 1;

        // when
        boolean result = optionPurchaseManageRepository.increasePurchaseCount(salesOption, purchaseCount, userId);

        // then
        assertThat(result).isTrue();
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(salesOption)).isEqualTo(purchaseCount);
    }

    @Test
    void 상품_구매수량_감소() {
        // given
        long userId = 1;
        int increaseCount = 3;
        int decreaseCount = 1;

        optionPurchaseManageRepository.increasePurchaseCount(salesOption, increaseCount, userId);
        optionPurchaseManageRepository.increasePurchaseCount(salesOption, increaseCount, userId);

        // when
        boolean result = optionPurchaseManageRepository.decreasePurchaseCount(salesOption, decreaseCount, userId);

        // then
        assertThat(result).isTrue();
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(salesOption))
                .isEqualTo(increaseCount * 2 - decreaseCount);
    }
}