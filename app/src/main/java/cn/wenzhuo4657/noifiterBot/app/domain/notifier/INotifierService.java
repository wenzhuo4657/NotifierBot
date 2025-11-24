package cn.wenzhuo4657.noifiterBot.app.domain.notifier;

public interface INotifierService {

    /**
     *  注册通信器
     *
     * @param paramsJson  通信器校验参数-json格式
     * @param type  通知器类型
     * @return  通信器索引（雪花算法实现）
     */
    long registerCommunicator(String paramsJson, String type);


    /**
     * 发送通信
     *
     * @param communicatorIndex  通信器索引
     * @param text  通知内容
     * @param type   通信器类型
     * @return true-成功，false-失败
     */
    boolean sendInfo(long communicatorIndex, String text, String type);


    /**
     * 检查通信器状态
     *
     * @param communicatorIndex 通信器索引
     * @return true-正常，false-异常
     */
    boolean checkCommunicatorStatus(long communicatorIndex);
}
