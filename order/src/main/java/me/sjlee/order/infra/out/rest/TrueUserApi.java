package me.sjlee.order.infra.out.rest;

import me.sjlee.order.service.UserApi;
import org.springframework.stereotype.Component;

@Component
public class TrueUserApi implements UserApi {
    @Override
    public boolean isValidUser(String userId) {
        return true;
    }
}
