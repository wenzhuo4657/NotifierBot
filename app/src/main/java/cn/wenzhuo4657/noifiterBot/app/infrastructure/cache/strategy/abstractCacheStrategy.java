package cn.wenzhuo4657.noifiterBot.app.infrastructure.cache.strategy;

import cn.wenzhuo4657.noifiterBot.app.config.CacheConfiguration;

/**
 * 缓存抽象层，用于实现一些公用的方法，如key前缀构建。
 */
public abstract class abstractCacheStrategy implements CacheStrategy {
    CacheConfiguration cacheConfiguration;


    public abstractCacheStrategy(CacheConfiguration cacheConfiguration) {
        this.cacheConfiguration = cacheConfiguration;
        initialize();
    }


    /**
     * 构建key
     */
    public String buildKey(String key){
        return cacheConfiguration.getKeyPrefix() + key;
    }





}
