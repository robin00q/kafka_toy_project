package me.sjlee.order.service;

import lombok.RequiredArgsConstructor;
import me.sjlee.order.domain.models.Money;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderPaymentFinishedService {

    private final OrderRepository orderRepository;

    @Transactional
    public void paymentFinished(long orderId, int amount) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalStateException("결제가 될 수 없는 orderId 입니다. orderId = " + orderId));

        if (!order.getTotalAmounts().equals(new Money(amount))) {
            throw new IllegalStateException("결제된 주문의 총 금액이 다릅니다. orderId = " + orderId);
        }

        order.finishPayment();
    }

}
