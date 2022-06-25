package me.sjlee.order.infra.in.queue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.Acknowledgment;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentFinishedConsumerConfig {

    public static final String PAYMENTS_CONSUMER_GROUP = "payments_consumer_group";

    @Slf4j
    static class PaymentFinishedMessageListener implements AcknowledgingMessageListener<String, String> {

        @Override
        @KafkaListener(topics = "payments", groupId = PaymentFinishedConsumerConfig.PAYMENTS_CONSUMER_GROUP)
        public void onMessage(ConsumerRecord<String, String> data, Acknowledgment acknowledgment) {
            log.info("consume Record: {}", data.toString());
            acknowledgment.acknowledge();
        }
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.getContainerProperties().setMessageListener(listener());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        /**
         * 필수 옵션 (bootstrap.servers, key.serializer, value.serializer)
         */
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092,localhost:39092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        /**
         * 선택 옵션
         */
        props.put(ConsumerConfig.GROUP_ID_CONFIG, PAYMENTS_CONSUMER_GROUP);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public PaymentFinishedMessageListener listener() {
        return new PaymentFinishedMessageListener();
    }

}
