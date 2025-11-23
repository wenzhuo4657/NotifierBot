package cn.wenzhuo4657.noifiterBot.app.infrastructure.cache;

import cn.wenzhuo4657.noifiterBot.app.infrastructure.cache.strategy.CacheStrategy;
import cn.wenzhuo4657.noifiterBot.app.types.cache.CacheType;

public interface IGlobalCache {

    /**
     * 获取当前缓存策略
     */
    CacheStrategy getCurrentCacheStrategy();


    /**
     * 切换缓存策略,支持缓存数据的迁移
     *
     */
    Boolean switchCacheStrategy(CacheType cacheType);

}
