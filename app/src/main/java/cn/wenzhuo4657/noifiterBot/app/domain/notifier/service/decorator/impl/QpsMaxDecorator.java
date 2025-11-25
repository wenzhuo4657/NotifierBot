package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.decorator.impl;

import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.decorator.NotifierDecorator;
import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.INotifier;
import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.NotifierConfig;
import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.NotifierMessage;
import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.NotifierResult;

public class QpsMaxDecorator extends NotifierDecorator {


    public QpsMaxDecorator(INotifier notifier) {
        super(notifier);
    }

    @Override
    public NotifierResult send(NotifierMessage message) {
        return null;
    }

    @Override
    public NotifierConfig getConfig() {
        return super.getConfig();

    }


}
