package cn.wenzhuo4657.noifiterBot.app.infrastructure.cache;

import cn.wenzhuo4657.noifiterBot.app.infrastructure.cache.factory.IMultiCacheFactory;
import cn.wenzhuo4657.noifiterBot.app.infrastructure.cache.strategy.CacheStrategy;
import cn.wenzhuo4657.noifiterBot.app.types.cache.CacheType;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 全局缓存管理器
 * 基于策略模式实现，支持动态切换不同的缓存实现（Redis、Valkey等）
 */
@Component
public class GlobalCache  implements IGlobalCache {


    @Autowired
    private IMultiCacheFactory multiCacheFactory;


    @Override
    public CacheStrategy getCurrentCacheStrategy() {
        return multiCacheFactory.getCurrentCacheStrategy();
    }

    @Override
    public Boolean switchCacheStrategy(CacheType cacheType) {
//        todo 内部应当支持缓存数据的迁移，但目前仅仅是强制切换
        return multiCacheFactory.switchCacheStrategy(cacheType);
    }
}
