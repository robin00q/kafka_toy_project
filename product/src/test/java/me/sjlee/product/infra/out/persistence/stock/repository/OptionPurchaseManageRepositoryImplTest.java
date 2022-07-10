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
        // given: none

        // when
        boolean result = optionPurchaseManageRepository.increasePurchaseCount(salesOption, 1);

        // then
        assertThat(result).isTrue();
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(salesOption)).isEqualTo(1);
    }

    @Test
    void 상품_구매수량_감소() {
        // given
        optionPurchaseManageRepository.increasePurchaseCount(salesOption, 3);
        optionPurchaseManageRepository.increasePurchaseCount(salesOption, 3);

        // when
        boolean result = optionPurchaseManageRepository.decreasePurchaseCount(salesOption, 1);

        // then
        assertThat(result).isTrue();
        assertThat(optionPurchaseManageRepository.getCurrentPurchaseCount(salesOption)).isEqualTo(5);
    }
}