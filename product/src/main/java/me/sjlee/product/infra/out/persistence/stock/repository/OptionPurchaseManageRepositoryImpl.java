package me.sjlee.product.infra.out.persistence.stock.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesOption;
import me.sjlee.product.domain.repository.OptionPurchaseHistoryRepository;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class OptionPurchaseManageRepositoryImpl implements OptionPurchaseManageRepository {

    private static final String STOCK_KEY_PREFIX = "option:purchase:";

    private final RedisTemplate<String, String> redisTemplate;
    private final OptionPurchaseHistoryRepository optionPurchaseHistoryRepository;

    @Override
    @Transactional
    public boolean increasePurchaseCount(SalesOption salesOption, int purchaseCount) {
        optionPurchaseHistoryRepository.recordIncrease(salesOption, purchaseCount);

        String key = createKey(salesOption);
        Long totalPurchaseCount;

        try {
            totalPurchaseCount = redisTemplate.opsForValue().increment(key, purchaseCount);
        } catch (RedisConnectionFailureException e) {
            totalPurchaseCount = optionPurchaseHistoryRepository.getPurchaseCount(salesOption) + purchaseCount;
        }

        return totalPurchaseCount <= salesOption.getTotalStock();
    }

    @Override
    @Transactional
    public boolean decreasePurchaseCount(SalesOption salesOption, int purchaseCount) {
        optionPurchaseHistoryRepository.recordDecrease(salesOption, purchaseCount);

        String key = createKey(salesOption);
        Long totalPurchaseCount;

        try  {
            totalPurchaseCount = redisTemplate.opsForValue().decrement(key, purchaseCount);
        } catch (RedisConnectionFailureException e) {
            totalPurchaseCount = optionPurchaseHistoryRepository.getPurchaseCount(salesOption) - purchaseCount;
        }

        return totalPurchaseCount >= 0;
    }

    @Override
    public void initPurchaseCount(SalesOption salesOption) {
        try {
            redisTemplate.opsForValue().set(createKey(salesOption), "0");
        } catch (RedisConnectionFailureException e) {
            return;
        }
    }

    @Override
    public long getCurrentPurchaseCount(SalesOption salesOption) {
        try {
            return Long.parseLong(redisTemplate.opsForValue().get(createKey(salesOption)));
        } catch (RedisConnectionFailureException e) {
            return optionPurchaseHistoryRepository.getPurchaseCount(salesOption);
        }
    }

    private String createKey(SalesOption salesOption) {
        return STOCK_KEY_PREFIX + salesOption.getId();
    }
}
