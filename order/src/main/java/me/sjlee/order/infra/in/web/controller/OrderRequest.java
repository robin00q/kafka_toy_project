package me.sjlee.order.infra.in.web.controller;

import lombok.Getter;
import me.sjlee.order.domain.models.Address;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.models.OrderLine;
import me.sjlee.order.domain.models.Orderer;
import me.sjlee.order.domain.models.Receiver;
import me.sjlee.order.domain.models.ShippingInfo;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
public class OrderRequest {

    private OrdererRequest orderer;
    private List<OrderLineRequest> orderLines;
    private ShippingInfoRequest shippingInfo;

    public Order toOrder() {
        Orderer ordererDomain = new Orderer(orderer.userId, orderer.name);
        List<OrderLine> orderLinesDomain = orderLines.stream()
                .map(e -> new OrderLine(e.getProductId(), e.getPrice(), e.getQuantity()))
                .collect(Collectors.toList());
        ShippingInfo shippingInfoDomain = shippingInfo.toShippingInfo();

        return new Order(UUID.randomUUID().toString(),
                ordererDomain,
                orderLinesDomain,
                shippingInfoDomain);
    }

    public List<String> getProductIds() {
        return orderLines.stream()
                .map(e -> e.productId)
                .collect(Collectors.toList());
    }

    public String getOrdererId() {
        return orderer.userId;
    }

    @Getter
    class OrdererRequest {
        private String userId;
        private String name;
    }

    @Getter
    static class OrderLineRequest {
        private String productId;
        private int price;
        private int quantity;
    }

    @Getter
    static class ShippingInfoRequest {
        private AddressRequest address;
        private ReceiverRequest receiver;

        public ShippingInfo toShippingInfo() {
            Address addressDomain = new Address(address.address1, address.address2, address.zipCode);
            Receiver receiverDomain = new Receiver(receiver.name, receiver.phoneNumber);

            return new ShippingInfo(addressDomain, receiverDomain);
        }
    }

    static class AddressRequest {
        private String address1;
        private String address2;
        private String zipCode;
    }

    static class ReceiverRequest {
        private String name;
        private String phoneNumber;
    }
}
