package me.sjlee.payment.infra.out.queue;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 카프카 Procuder Config <br/>
 *
 * <a href="https://docs.confluent.io/platform/current/installation/configuration/producer-configs.html"/>ref</a>
 */
@Configuration
public class KafkaProducerConfig {

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> props = new HashMap<>();
        /**
         * 필수 옵션 (bootstrap.servers, key.serializer, value.serializer)
         */
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092"); // 1개 이상의 브로커를 작성할 수 있다.
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // 키 직렬화 클래스 명시
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class); // 값 직렬화 클래스 명시

        /**
         * 선택 옵션
         */
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); // 멱등성 프로듀서로 작동하도록 하여 한번 저장되도록 한다.
        props.put(ProducerConfig.ACKS_CONFIG, "-1"); // 리더 파티션, 팔로워 파티션 모두에 저장됐음을 확인한다.

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
