package me.sjlee.order.domain.repository;

import me.sjlee.order.domain.models.Address;
import me.sjlee.order.domain.models.Money;
import me.sjlee.order.domain.models.Order;
import me.sjlee.order.domain.models.OrderLine;
import me.sjlee.order.domain.models.Orderer;
import me.sjlee.order.domain.models.Receiver;
import me.sjlee.order.domain.models.ShippingInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderRepositoryTest {

    @Autowired private OrderRepository orderRepository;

    private Orderer orderer;
    private List<OrderLine> orderLines;
    private ShippingInfo shippingInfo;

    @BeforeEach
    void setUp() {
        orderer = new Orderer(1L, "이석준");

        orderLines = Arrays.asList(
                new OrderLine(1L, 1L, 10000, 10),
                new OrderLine(2L, 1L, 20000, 20)
        );

        shippingInfo = new ShippingInfo(
                new Address("서울시 관악구", "805호", "12321"),
                new Receiver("이석준", "010-1234-1234")
        );
    }

    @Test
    void 주문_저장_테스트() {
        // given
        Order order = new Order(orderer, orderLines, shippingInfo);

        // when
        Order saved = orderRepository.save(order);

        // then
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void findOrderByIdAndTotalAmounts() {
        // given
        Order saved = orderRepository.save(new Order(orderer, orderLines, shippingInfo));

        // when
        Optional<Order> findOrder = orderRepository.findOrderByIdAndTotalAmounts(saved.getId(), saved.getTotalAmounts());

        // then
        assertThat(findOrder.isPresent()).isTrue();
        assertThat(findOrder.get().getTotalAmounts()).isEqualTo(new Money(500000));
    }
}