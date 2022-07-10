package me.sjlee.product.infra.out.persistence.stock.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OptionPurchaseManageRepositoryImpl implements OptionPurchaseManageRepository {

    private static final String STOCK_KEY_PREFIX = "option:purchase:";

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean increasePurchaseCount(SalesOption salesOption, int purchaseCount) {
        String key = STOCK_KEY_PREFIX + salesOption.getId();
        Long totalPurchaseCount = redisTemplate.opsForValue().increment(key, purchaseCount);

        return totalPurchaseCount > salesOption.getTotalStock();
    }

    @Override
    public void decreasePurchaseCount(SalesOption salesOption, int purchaseCount) {
        String key = STOCK_KEY_PREFIX + salesOption.getId();
        redisTemplate.opsForValue().decrement(key, purchaseCount);
    }
}
