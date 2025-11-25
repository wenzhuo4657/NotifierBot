package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.factory;

import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.INotifier;

public interface IPondFactory {

    /**
     * 初始化通知器，返回索引key
     *
     * @param json   配置json字符串
     * @param type   初始化类型
     * @return   索引key
     */
    public long init(String json,String type);


    /**
     * 获取通知器
     */
    public  INotifier get(long key);
}
