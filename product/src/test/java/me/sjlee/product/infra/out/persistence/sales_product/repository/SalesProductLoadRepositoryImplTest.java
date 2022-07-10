package me.sjlee.product.infra.out.persistence.sales_product.repository;

import me.sjlee.product.domain.models.Money;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionStatus;
import me.sjlee.product.domain.models.SalesProduct;
import me.sjlee.product.domain.repository.SalesProductLoadRepository;
import me.sjlee.product.domain.repository.SalesProductSaveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class SalesProductLoadRepositoryImplTest {

    @Autowired
    private SalesProductSaveRepository salesProductSaveRepository;

    @Autowired
    private SalesProductLoadRepository salesProductLoadRepository;

    private SalesProduct salesProduct;

    @BeforeEach
    void setUp() {
        SalesOption option1 = SalesOption.builder()
                .id(1L)
                .name("석준치킨 10000원 10%할인")
                .totalStock(100)
                .price(new Money(10000))
                .discountRate(10)
                .status(SalesOptionStatus.ON_SALE)
                .build();

        SalesOption option2 = SalesOption.builder()
                .id(2L)
                .name("석준치킨 10000원")
                .totalStock(9999999)
                .price(new Money(10000))
                .discountRate(0)
                .status(SalesOptionStatus.ON_SALE)
                .build();

        salesProduct = SalesProduct.builder()
                .id(1L)
                .name("석준치킨")
                .sellerId(1L)
                .salesOptions(Arrays.asList(option1, option2))
                .build();
    }

    @DisplayName("판매상품과 옵션정보를 로딩한다.")
    @Test
    void load() {
        // given
        SalesProduct saved = salesProductSaveRepository.record(salesProduct);

        // when
        Optional<SalesProduct> loaded = salesProductLoadRepository.findById(saved.getId());

        // then
        assert(loaded.isPresent());
        assertThat(loaded.get().getId()).isEqualTo(saved.getId());
        assertThat(loaded.get().getSalesOptions()).isEqualTo(saved.getSalesOptions());
    }
}