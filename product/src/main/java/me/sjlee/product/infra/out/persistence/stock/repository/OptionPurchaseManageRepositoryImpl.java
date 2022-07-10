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
        String key = createKey(salesOption);
        Long totalPurchaseCount = redisTemplate.opsForValue().increment(key, purchaseCount);

        return totalPurchaseCount <= salesOption.getTotalStock();
    }

    @Override
    public boolean decreasePurchaseCount(SalesOption salesOption, int purchaseCount) {
        String key = createKey(salesOption);
        Long totalPurchaseCount = redisTemplate.opsForValue().decrement(key, purchaseCount);

        return totalPurchaseCount >= 0;
    }

    @Override
    public void initPurchaseCount(SalesOption salesOption) {
        redisTemplate.opsForValue().set(createKey(salesOption), "0");
    }

    @Override
    public int getCurrentPurchaseCount(SalesOption salesOption) {
        return Integer.parseInt(redisTemplate.opsForValue().get(createKey(salesOption)));
    }

    private String createKey(SalesOption salesOption) {
        return STOCK_KEY_PREFIX + salesOption.getId();
    }
}
