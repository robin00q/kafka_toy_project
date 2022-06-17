package me.sjlee.order.service;

import me.sjlee.order.infra.in.web.controller.OrderRequest;

public interface UserApi {
    boolean isValidUser(String userId);
}
