package cn.wenzhuo4657.noifiterBot.app.domain.notifier;

import cn.wenzhuo4657.noifiterBot.app.domain.notifier.model.vo.ConfigType;
import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.factory.IPondFactory;
import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.INotifier;
import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.NotifierMessage;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NormallyService implements  INotifierService{

    @Autowired
    private IPondFactory pondFactory;
    @Override
    public long registerCommunicator(String paramsJson, String type,String [] decorator) {
        return  pondFactory.init(paramsJson,type,decorator);

    }

    @Override
    public boolean sendInfo(long communicatorIndex, String paramsJson,String type) {
        ConfigType.Strategy strategy = ConfigType.Strategy.find(type);
        Class<? extends NotifierMessage> messageClass = strategy.getMessageClass();
        NotifierMessage message = JSON.parseObject(paramsJson, messageClass);
        INotifier notifier = pondFactory.get(communicatorIndex);
        notifier.send(message);
        return true;
    }

    @Override
    public boolean checkCommunicatorStatus(long communicatorIndex) {
        return true;
    }

    @Override
    public List<String> querySupportNotifier() {
        ConfigType.Strategy[] strategies = ConfigType.Strategy.values();
        List<String> list = new ArrayList<>();
        for (ConfigType.Strategy strategy : strategies) {
            list.add(strategy.getCode());
        }
        return list;
    }

    @Override
    public List<String> querySupportDecorator() {
        ConfigType.Decorator[] decorators = ConfigType.Decorator.values();
        List<String> list = new ArrayList<>();
        for (ConfigType.Decorator decorator : decorators) {
            list.add(decorator.getCode());
        }
        return list;
    }
}
