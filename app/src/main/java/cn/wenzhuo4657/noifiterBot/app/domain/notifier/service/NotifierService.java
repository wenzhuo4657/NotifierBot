package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service;

import cn.wenzhuo4657.noifiterBot.app.domain.notifier.INotifierService;
import org.springframework.stereotype.Service;

@Service
public class NotifierService implements INotifierService {

    @Override
    public long registerCommunicator(String paramsJson, String type) {
        return 0;
    }

    @Override
    public boolean sendInfo(long communicatorIndex, String text, String type) {
        return false;
    }


    @Override
    public boolean checkCommunicatorStatus(long communicatorIndex) {
        return false;
    }
}
