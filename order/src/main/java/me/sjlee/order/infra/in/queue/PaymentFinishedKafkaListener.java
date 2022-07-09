package me.sjlee.order.infra.in.queue;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.order.service.OrderPaymentFinishedService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentFinishedKafkaListener implements AcknowledgingMessageListener<String, String> {

    private final OrderPaymentFinishedService orderPaymentFinishedService;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "payments",
            groupId = PaymentFinishedConsumerConfig.PAYMENTS_CONSUMER_GROUP)
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
        log.info("consume Record: {}", data.value());

        try {
            PaymentFinishedRecord record =
                    objectMapper.readValue(data.value(), PaymentFinishedRecord.class);
            orderPaymentFinishedService.paymentFinished(
                    record.getOrderId(), record.getAmount());

            // 예외가 발생하지 않는다면, 수동커밋을 진행하여 읽은 메세지는 처리됐음을 보장한다.!
            acknowledgment.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}