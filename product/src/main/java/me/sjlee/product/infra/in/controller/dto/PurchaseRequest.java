package me.sjlee.product.infra.in.controller.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@Getter
@Builder
public class PurchaseRequest {

    @Min(1)
    private long orderId;
    @Min(1)
    private long userId;
    @Valid
    private List<PurchaseOptionRequest> purchaseOptionRequests;

    @Getter
    @Builder
    public static class PurchaseOptionRequest {

        @Min(1)
        private long productId;
        @Min(1)
        private long optionId;
        @Min(1)
        private int quantity;
    }

}
