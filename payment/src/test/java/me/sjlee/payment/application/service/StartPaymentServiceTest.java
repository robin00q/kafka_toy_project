package me.sjlee.payment.application.service;

import me.sjlee.payment.application.client.OrderClient;
import me.sjlee.payment.application.controller.StartPaymentRequest;
import me.sjlee.payment.domain.models.PayedBy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class StartPaymentServiceTest {

    @Autowired private StartPaymentService startPaymentService;
    @MockBean private OrderClient orderClient;

    @Test
    void test() {
        // given
        given(orderClient.isValidOrder(any(), any())).willReturn(true);
        StartPaymentRequest request = new StartPaymentRequest(1L, 100000, PayedBy.CREDIT_CARD);

        // when
        startPaymentService.startPayment(request);

        // then
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}