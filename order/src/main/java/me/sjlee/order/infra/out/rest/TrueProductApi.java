package me.sjlee.order.infra.out.rest;

import me.sjlee.order.service.ProductApi;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TrueProductApi implements ProductApi {
    @Override
    public boolean isValidProducts(List<Long> productId) {
        return true;
    }
}
