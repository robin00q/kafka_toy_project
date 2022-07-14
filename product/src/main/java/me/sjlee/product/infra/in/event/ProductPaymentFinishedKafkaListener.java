package me.sjlee.product.infra.in.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.sjlee.product.application.service.StockManageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductPaymentFinishedKafkaListener implements AcknowledgingMessageListener<String, String> {

    private final StockManageService stockManageService;
    private final ObjectMapper objectMapper;

    @Override
    @KafkaListener(topics = "payments",
            groupId = ProductPaymentFinishedConsumerConfig.PAYMENTS_CONSUMER_GROUP)
    public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
        log.info("consume Record: {}", data.value());

        try {
            ProductPaymentFinishedRecord record =
                    objectMapper.readValue(data.value(), ProductPaymentFinishedRecord.class);
            stockManageService.increaseStock(record.getOrderId());

            // 예외가 발생하지 않는다면, 수동커밋을 진행하여 읽은 메세지는 처리됐음을 보장한다.!
            acknowledgment.acknowledge();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}