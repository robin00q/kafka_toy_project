package me.sjlee.order.infra.out.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import me.sjlee.order.infra.out.rest.dto.ProductValidateRequest;
import me.sjlee.order.infra.out.rest.dto.ProductValidateResponse;
import me.sjlee.order.service.api.ProductApi;
import me.sjlee.order.service.dto.ProductValidateDto;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ProductApiImpl implements ProductApi {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public boolean canPurchase(List<ProductValidateDto> optionId) {
        String url = "http://localhost:8082/product/validate";

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        header.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        String body = null;

        try {
            body = objectMapper.writeValueAsString(new ProductValidateRequest(optionId));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        HttpEntity<String> entity = new HttpEntity<>(body, header);

        return Boolean.TRUE.equals(Objects.requireNonNull(restTemplate.postForEntity(url, entity, ProductValidateResponse.class).getBody())
                .isValid());
    }
}
