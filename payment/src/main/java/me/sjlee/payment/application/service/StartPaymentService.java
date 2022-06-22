package me.sjlee.payment.application.service;

import lombok.RequiredArgsConstructor;
import me.sjlee.payment.application.client.OrderClient;
import me.sjlee.payment.application.controller.StartPaymentRequest;
import me.sjlee.payment.domain.models.Money;
import me.sjlee.payment.domain.models.Payment;
import me.sjlee.payment.domain.repository.MoneyRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StartPaymentService {

    private final MoneyRepository moneyRepository;
    private final OrderClient orderClient;

    public Long startPayment(StartPaymentRequest request) {
        validateOrder(request);

        Payment payment = new Payment(request.getOrderId(), new Money(request.getAmounts()), request.getPayedBy());
        // 저장을 통해 결제가 됐다고 가정한다.
        moneyRepository.save(payment);

        // TODO : send payment to kafka
        payment.finishPayment();

        return payment.getId();
    }

    private void validateOrder(StartPaymentRequest request) {
        boolean isValid = orderClient.isValidOrder(request.getOrderId(), new Money(request.getAmounts()));
        if (!isValid) {
            throw new IllegalStateException("결제가 불가능한 주문입니다.");
        }
    }
}
