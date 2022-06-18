package me.sjlee.payment.domain.repository;

import me.sjlee.payment.domain.models.Money;
import me.sjlee.payment.domain.models.PayedBy;
import me.sjlee.payment.domain.models.Payment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MoneyRepositoryTest {

    @Autowired private MoneyRepository moneyRepository;

    @Test
    void 결제_저장_테스트() {
        // given
        Payment payment = new Payment(1L, new Money(100000), PayedBy.CREDIT_CARD);

        // when
        Payment saved = moneyRepository.save(payment);

        // then
        assertThat(saved.getId()).isNotNull();
    }
}