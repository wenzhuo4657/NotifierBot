package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy;


/**
 * 通知器策略接口定义
 *
 *  * todo 暂时不涉及通知器复用，后续如果想要复用需要考虑 （1）通知配置和通知器解耦，（2）设计模式 工厂模式、管理器模式
 *
 */
public abstract class INotifier {

     NotifierConfig config;


    public INotifier(NotifierConfig config) {
        this.config = config;
    }


    public INotifier() {
    }

    /**
     * 发送通知
     */
    public abstract NotifierResult send(NotifierMessage message);


    /**
     * 通知器是否可用
     */
    public abstract boolean isAvailable();

    /**
     * 销毁资源
     */
    public abstract void destroy();


    public NotifierConfig getConfig() {
        return config;
    }
}
