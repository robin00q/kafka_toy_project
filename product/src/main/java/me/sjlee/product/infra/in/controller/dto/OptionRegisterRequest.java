package me.sjlee.product.infra.in.controller.dto;

import lombok.Getter;
import me.sjlee.product.domain.models.Money;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.models.SalesOptionStatus;

import java.util.List;

@Getter
public class OptionRegisterRequest {

    private long productId;
    private List<OptionRequest> options;

    @Getter
    public static class OptionRequest {
        private String name;
        private int totalStock;
        private int price;
        private int discountRate;

        public SalesOption toOption() {
            return SalesOption.builder()
                    .name(name)
                    .totalStock(totalStock)
                    .price(new Money(price))
                    .discountRate(discountRate)
                    .status(SalesOptionStatus.ON_SALE)
                    .build();
        }
    }
}
