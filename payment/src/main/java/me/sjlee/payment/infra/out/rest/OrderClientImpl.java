package me.sjlee.payment.infra.out.rest;

import lombok.RequiredArgsConstructor;
import me.sjlee.payment.application.client.OrderClient;
import me.sjlee.payment.domain.models.Money;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OrderClientImpl implements OrderClient {

    private final RestTemplate restTemplate;

    @Override
    public boolean isValidOrder(Long orderId, Money money) {
        String url = "http://localhost:8080/order/valid?orderId={orderId}&amounts={amounts}";

        Map<String, String> params = new HashMap<>();
        params.put("orderId", orderId.toString());
        params.put("amounts", String.valueOf(money.getValue()));

        return Boolean.TRUE.equals(restTemplate.getForEntity(url, Boolean.class, params).getBody());
    }
}
