package me.sjlee.product.infra.out.client.order;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.application.client.order.OrderClient;
import me.sjlee.product.application.client.order.OrderDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderClientImpl implements OrderClient {

    private final RestTemplate restTemplate;

    @Override
    public OrderDetail getOrderDetail(long orderId) {
        String url = "http://localhost:8080/order/detail?orderId={orderId}";

        Map<String, String> params = new HashMap<>();
        params.put("orderId", String.valueOf(orderId));

        return restTemplate.getForEntity(url, OrderDetail.class, params).getBody();
    }
}
