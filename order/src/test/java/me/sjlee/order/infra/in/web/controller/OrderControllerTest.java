package me.sjlee.order.infra.in.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.sjlee.order.domain.models.Money;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.models.OrderStatus;
import me.sjlee.order.domain.repository.OrderRepository;
import me.sjlee.order.infra.in.web.controller.dto.StartOrderRequest;
import me.sjlee.order.service.api.ProductApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper mapper;
    @Autowired private OrderRepository orderRepository;
    @MockBean private ProductApi productApi;

    StartOrderRequest.OrdererRequest ordererRequest;
    List<StartOrderRequest.OrderLineRequest> orderLineRequests;
    StartOrderRequest.ShippingInfoRequest shippingInfoRequest;

    @BeforeEach
    void setUp() {
        ordererRequest = new StartOrderRequest.OrdererRequest(1L, "이석준");

        orderLineRequests = Arrays.asList(
                new StartOrderRequest.OrderLineRequest(1L, 1L, 10000, 10),
                new StartOrderRequest.OrderLineRequest(2L, 1L, 20000, 20)
        );

        shippingInfoRequest = new StartOrderRequest.ShippingInfoRequest(
                new StartOrderRequest.AddressRequest("서울시 관악구", "806호", "12345"),
                new StartOrderRequest.ReceiverRequest("이석준", "010-1111-1111")
        );

    }

    @Test
    void 주문_시작_integration_test() throws Exception {
        // given
        given(productApi.canPurchase(any())).willReturn(true);
        StartOrderRequest request = new StartOrderRequest(ordererRequest, orderLineRequests, shippingInfoRequest);

        // when
        String orderId = mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/order/start")
                                .content(mapper.writeValueAsString(request))
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn()
                .getResponse().getContentAsString();

        // then
        Order order = orderRepository.findById(Long.valueOf(orderId)).get();
        assertNotNull(order);
        assertThat(order.getTotalAmounts()).isEqualTo(new Money(500000));
        assertThat(order.getStatus()).isEqualTo(OrderStatus.PAYMENT_WAITING);
    }
}