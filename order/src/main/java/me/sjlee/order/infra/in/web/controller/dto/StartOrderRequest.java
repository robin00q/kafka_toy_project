package me.sjlee.order.infra.in.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.sjlee.order.domain.models.Address;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.models.OrderLine;
import me.sjlee.order.domain.models.Orderer;
import me.sjlee.order.domain.models.Receiver;
import me.sjlee.order.domain.models.ShippingInfo;
import me.sjlee.order.service.dto.ProductValidateDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class StartOrderRequest {

    private OrdererRequest orderer;
    private List<OrderLineRequest> orderLines;
    private ShippingInfoRequest shippingInfo;

    public Order toOrder() {
        Orderer ordererDomain = new Orderer(orderer.userId, orderer.name);
        List<OrderLine> orderLinesDomain = orderLines.stream()
                .map(e -> new OrderLine(e.getProductId(), e.getOptionId(), e.getPrice(), e.getQuantity()))
                .collect(Collectors.toList());
        ShippingInfo shippingInfoDomain = shippingInfo.toShippingInfo();

        return new Order(
                ordererDomain,
                orderLinesDomain,
                shippingInfoDomain);
    }

    public List<ProductValidateDto> toProductValidateDto() {
        return orderLines.stream()
                .map(e -> new ProductValidateDto(e.getProductId(), e.getOptionId(), e.getQuantity()))
                .collect(Collectors.toList());
    }

    public Long toOrdererId() {
        return orderer.userId;
    }

    @Getter
    @AllArgsConstructor
    public static class OrdererRequest {
        private Long userId;
        private String name;
    }

    @Getter
    @AllArgsConstructor
    public static class OrderLineRequest {
        private Long productId;
        private long optionId;
        private int price;
        private int quantity;
    }

    @Getter
    @AllArgsConstructor
    public static class ShippingInfoRequest {
        private AddressRequest address;
        private ReceiverRequest receiver;

        public ShippingInfo toShippingInfo() {
            Address addressDomain = new Address(address.address1, address.address2, address.zipCode);
            Receiver receiverDomain = new Receiver(receiver.name, receiver.phoneNumber);

            return new ShippingInfo(addressDomain, receiverDomain);
        }
    }

    @Getter
    @AllArgsConstructor
    public static class AddressRequest {
        private String address1;
        private String address2;
        private String zipCode;
    }

    @Getter
    @AllArgsConstructor
    public static class ReceiverRequest {
        private String name;
        private String phoneNumber;
    }
}
