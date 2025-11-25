package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy;


/**
 * 通知器策略接口定义
 *
 *  * todo 暂时不涉及通知器复用，后续如果想要复用需要考虑 （1）通知配置和通知器解耦，（2）设计模式 工厂模式、管理器模式
 *
 */
public  interface INotifier<T extends  NotifierConfig, R extends NotifierMessage,H extends  NotifierResult> {



    /**
     * 发送通知
     */
    public abstract H send(R message);


    /**
     * 通知器是否可用
     */
    public abstract boolean isAvailable();

    /**
     * 销毁资源
     */
    public abstract void destroy();


    public T getConfig();


    /**
     * 获取通知起允许的最大qps
     */
    public  int getQps();
}
