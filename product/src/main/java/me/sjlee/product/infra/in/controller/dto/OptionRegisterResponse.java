package me.sjlee.product.infra.in.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OptionRegisterResponse {

    List<Long> optionIds;
}
