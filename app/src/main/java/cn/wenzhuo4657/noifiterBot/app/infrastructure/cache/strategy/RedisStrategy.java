package cn.wenzhuo4657.noifiterBot.app.infrastructure.cache.strategy;

import java.util.concurrent.TimeUnit;

import cn.wenzhuo4657.noifiterBot.app.config.CacheConfiguration;
import cn.wenzhuo4657.noifiterBot.app.config.CacheConfiguration.Redis;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedisStrategy  extends   abstractCacheStrategy{

    RedissonClient redissonClient;


    public RedisStrategy(CacheConfiguration cacheConfiguration) {
        super(cacheConfiguration);
    }


    @Override
    public void initialize() {
        Redis redis = cacheConfiguration.getRedis();
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://"+redis.getHost()+":"+redis.getPort())
                .setDatabase(redis.getDatabase())
                .setConnectionPoolSize(redis.getConnectionPoolSize())
                .setConnectionMinimumIdleSize(redis.getConnectionMinimumIdleSize())
                .setTimeout(redis.getTimeout())
                .setRetryAttempts(redis.getRetryAttempts())
                .setRetryInterval(redis.getRetryInterval());
        if(!StringUtils.isEmpty(redis.getPassword()))
            config.useSingleServer().setPassword(redis.getPassword());

        redissonClient = Redisson.create(config);

    }

    @Override
    public void set(String key, Object value) {

    }

    @Override
    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {

    }

    @Override
    public <T> T get(String key, Class<T> clazz) {
        return null;
    }

    @Override
    public Boolean delete(String key) {
        return null;
    }

    @Override
    public Boolean hasKey(String key) {
        return null;
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return 0L;
    }

    @Override
    public void flushAll() {

    }


    @Override
    public void shutdown() {

    }

    @Override
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return null;
    }
}
