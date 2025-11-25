package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service;

import cn.wenzhuo4657.noifiterBot.app.domain.notifier.INotifierService;
import org.springframework.stereotype.Service;

@Service
public class NotifierService implements INotifierService {


    @Override
    public long registerCommunicator(String paramsJson, String type) {
//        1,校验参数
//        2，生成索引并存储k,v
        return 0;
    }

    @Override
    public boolean sendInfo(long communicatorIndex, String text, String type) {
//        1,根据索引发送信息，
//        2，抽调公有逻辑（例如qps限制）和api实现
        return false;
    }


    @Override
    public boolean checkCommunicatorStatus(long communicatorIndex) {
//        1，检查qps是否满载
        return false;
    }
}
