package me.sjlee.payment.infra.out.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.payment.domain.event.PaymentFinishedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPaymentFinishedEventHandler {

    private static final String PAYMENT_FINISHED_TOPIC = "payments";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @EventListener(PaymentFinishedEvent.class)
    @Async
    public void handle(PaymentFinishedEvent event) {
        log.info("send kafka message to topic : {}, orderId : {}", PAYMENT_FINISHED_TOPIC, event.getOrderId());

        OrderPaymentFinishedRecord orderPaymentFinishedRecord = new OrderPaymentFinishedRecord(event.getOrderId(), event.getAmount().getValue());

        try {
            kafkaTemplate.send(PAYMENT_FINISHED_TOPIC, objectMapper.writeValueAsString(orderPaymentFinishedRecord));
        } catch (JsonProcessingException e) {
            log.error("[결제완료] 카프카 이벤트 전송 실패");
        }
    }
}
