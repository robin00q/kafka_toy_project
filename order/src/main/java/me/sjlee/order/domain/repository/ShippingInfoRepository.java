package me.sjlee.order.domain.repository;

import me.sjlee.order.domain.models.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, Long> {
}
