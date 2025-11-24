package cn.wenzhuo4657.noifiterBot.app.infrastructure.cache.strategy;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import cn.wenzhuo4657.noifiterBot.app.config.CacheConfiguration;
import cn.wenzhuo4657.noifiterBot.app.config.CacheConfiguration.Redis;
import org.redisson.api.RScript;
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
        if (redissonClient == null || redissonClient.isShutdown()) {
            return false;
        }

        try {
            // 轻量级连接检测：执行一个简单的操作来验证连接
            // 使用Redisson的getNodesGroup().pingAll()来检测所有节点连接状态
            return redissonClient.getNodesGroup().pingAll();
        } catch (Exception e) {
            // 连接异常或超时，返回false
            return false;
        }
    }
    @Override
    public void flushAll() {
        if (!isAvailable()) {
            throw new RuntimeException("Redis连接不可用，无法执行flushAll操作");
        }

        try {

            // 执行清空当前数据库的命令
            redissonClient.getScript().eval(
                RScript.Mode.READ_WRITE,
                "return redis.call('FLUSHDB')",
                RScript.ReturnType.STATUS
            );
        } catch (Exception e) {
            throw new RuntimeException("执行flushAll操作失败: " + e.getMessage(), e);
        }
    }


    @Override
    public void shutdown() {
        if (redissonClient == null) {
            return; // 已经关闭或未初始化
        }

        try {
            // 检查连接状态
            if (!redissonClient.isShutdown()) {
                // 优雅关闭：等待正在执行的操作完成
                redissonClient.shutdown(3, 5, java.util.concurrent.TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            // 强制关闭
            try {
                redissonClient.shutdown();
            } catch (Exception shutdownException) {
                // 记录错误但不抛出异常，确保关闭过程能够完成
                System.err.println("Redis强制关闭时发生错误: " + shutdownException.getMessage());
            }
        } finally {
            // 清理引用
            redissonClient = null;
        }
    }

    @Override
    public Long getExpire(String key, TimeUnit timeUnit) {
        return 0L;
    }



    @Override
    public Boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return null;
    }

    @Override
    public <T> T executeLuaScript(String luaScript, List<String> keys, List<Object> values, Class<T> resultType) {
        if (!isAvailable()) {
            throw new RuntimeException("Redis连接不可用，无法执行Lua脚本");
        }

        try {
            RScript script = redissonClient.getScript();

            // 使用Redisson的标准API格式：keys和values分别传递
            List<Object> keysObjects = keys != null ?
                keys.stream().map(key -> (Object) key).collect(Collectors.toList()) : Collections.emptyList();

            Object[] valuesArray = values != null ? values.toArray() : new Object[0];

            Object result = script.eval(RScript.Mode.READ_WRITE,
                                       luaScript,
                                       RScript.ReturnType.VALUE,
                                       keysObjects,
                                       valuesArray);

            return resultType != null ? resultType.cast(result) : (T) result;
        } catch (Exception e) {
            throw new RuntimeException("执行Lua脚本失败: " + e.getMessage(), e);
        }
    }

    @Override
    public <T> T executeLuaScriptSha1(String scriptSha1, List<String> keys, List<Object> values, Class<T> resultType) {
        if (!isAvailable()) {
            throw new RuntimeException("Redis连接不可用，无法执行Lua脚本");
        }

        try {
            RScript script = redissonClient.getScript();

            // 使用Redisson的标准API格式：keys和values分别传递
            List<Object> keysObjects = keys != null ?
                keys.stream().map(key -> (Object) key).collect(Collectors.toList()) : Collections.emptyList();

            Object[] valuesArray = values != null ? values.stream().map(v -> v.toString()).toArray() : new Object[0];

            Object result = script.evalSha(RScript.Mode.READ_WRITE,
                                         scriptSha1,
                                         RScript.ReturnType.VALUE,
                                         keysObjects,
                                         valuesArray);

            return resultType != null ? resultType.cast(result) : (T) result;
        } catch (Exception e) {
            throw new RuntimeException("执行Lua脚本(SHA1)失败: " + e.getMessage(), e);
        }
    }

    @Override
    public String loadLuaScript(String luaScript) {
        if (!isAvailable()) {
            throw new RuntimeException("Redis连接不可用，无法加载Lua脚本");
        }

        try {
            RScript script = redissonClient.getScript();
            return script.scriptLoad(luaScript);
        } catch (Exception e) {
            throw new RuntimeException("加载Lua脚本失败: " + e.getMessage(), e);
        }
    }
}
