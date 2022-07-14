package me.sjlee.product.infra.out.persistence.stock.repository;

import lombok.RequiredArgsConstructor;
import me.sjlee.product.domain.models.SalesOptionPurchaseRecord;
import me.sjlee.product.domain.repository.OptionPurchaseHistoryRepository;
import me.sjlee.product.domain.repository.OptionPurchaseManageRepository;
import me.sjlee.product.infra.out.persistence.stock.dto.OptionPurchaseHistoryDataModel;
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
    public boolean increaseStock(SalesOptionPurchaseRecord record) {
        optionPurchaseHistoryRepository.save(OptionPurchaseHistoryDataModel.increase(record));

        String key = createKey(record.getProductId(), record.getOptionId());
        Long totalPurchaseCount;

        try {
            totalPurchaseCount = redisTemplate.opsForValue().increment(key, record.getQuantity());
        } catch (RedisConnectionFailureException e) {
            totalPurchaseCount = optionPurchaseHistoryRepository.getPurchaseCount(record.getProductId(), record.getOptionId());
        }

        return totalPurchaseCount <= record.getTotalStock();
    }

    @Override
    @Transactional
    public boolean decreaseStock(SalesOptionPurchaseRecord record) {
        optionPurchaseHistoryRepository.save(OptionPurchaseHistoryDataModel.decrease(record));

        String key = createKey(record.getProductId(), record.getOptionId());
        Long totalPurchaseCount;

        try  {
            totalPurchaseCount = redisTemplate.opsForValue().decrement(key, record.getQuantity());
        } catch (RedisConnectionFailureException e) {
            totalPurchaseCount = optionPurchaseHistoryRepository.getPurchaseCount(record.getProductId(), record.getOptionId());
        }

        return totalPurchaseCount >= 0;
    }

    @Override
    public long getCurrentPurchaseCount(long productId, long optionId) {
        String key = createKey(productId, optionId);

        try {
            return Long.parseLong(redisTemplate.opsForValue().get(key));
        } catch (RedisConnectionFailureException e) {
            return optionPurchaseHistoryRepository.getPurchaseCount(productId, optionId);
        }
    }

    @Override
    public void initPurchaseCount(long productId, long optionId) {
        String key = createKey(productId, optionId);

        try {
            redisTemplate.opsForValue().set(key, "0");
        } catch (RedisConnectionFailureException e) {
            return;
        }
    }

    private String createKey(long productId, long optionId) {
        return STOCK_KEY_PREFIX + productId + ":" + optionId;
    }
}
