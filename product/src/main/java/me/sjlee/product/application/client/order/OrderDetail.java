package me.sjlee.product.application.client.order;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderDetail {

    private long orderId;
    private long userId;
    private List<OrderLineDetail> orderLines;

    @Getter
    public static class OrderLineDetail {
        private long productId;
        private long optionId;
        private int quantity;
    }
}
