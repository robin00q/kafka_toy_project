package me.sjlee.order.service;

import java.util.List;

public interface ProductApi {

    boolean isValidProducts(List<Long> productId);
}
