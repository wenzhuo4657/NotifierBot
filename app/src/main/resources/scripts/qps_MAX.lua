--[[
    QPS 限制脚本
    功能：通过 Redis Lua 脚本实现 QPS 限制功能
    参数：
    - KEYS[1]: 键名
    - ARGV[1]: 最大 QPS 限制值

    执行逻辑：
    - 如果键不存在，创建键并赋值为 1
    - 如果键存在，检查当前值是否小于等于最大值
    - 如果当前值小于最大值，则增加 1
    - 如果当前值已达到最大值，则返回错误状态

    返回值：
    - 1: 表示操作成功（QPS 未超限）
    - 0: 表示操作失败（QPS 已达到最大值）
    - current_value: 当前计数值
    - current_qps: 当前 QPS 值
--]]

-- 获取传入的参数
local key_name = KEYS[1]
local max_qps = tonumber(ARGV[1])


-- 检查参数有效性
if not key_name or not max_qps or max_qps <= 0 then
        local error_message = "Invalid parameters received. key_name: " .. tostring(key_name) .. ", max_qps: " .. tostring(max_qps) .. ". Both must be provided and max_qps must be positive."
        return redis.error_reply(error_message)
end

-- 获取当前计数
local current_value = redis.call('GET', key_name)

-- 如果键不存在，创建新键并设置初始值为 1，过期时间为 1 秒
if current_value == false then
    redis.call('SET', key_name, 1, 'EX', 1)
    return {
        1,           -- 操作状态：成功
        1,           -- 当前值
        1,           -- 当前 QPS
        'new_created'-- 状态描述：新建键
    }
end

-- 转换为数字
current_value = tonumber(current_value)

-- 检查当前值是否小于最大值
if current_value < max_qps then
    -- 增加计数
    local new_value = redis.call('INCR', key_name)
    -- 如果是第一次设置，确保有过期时间
    if new_value == 1 then
        redis.call('EXPIRE', key_name, 1)
    end
    return {
        1,               -- 操作状态：成功
        new_value,       -- 当前值
        new_value,       -- 当前 QPS
        'increased'      -- 状态描述：增加成功
    }
else
    -- 已达到最大值，不允许继续增加
    return {
        0,               -- 操作状态：失败
        current_value,   -- 当前值
        current_value,   -- 当前 QPS
        'limit_reached'  -- 状态描述：已达到限制
    }
end