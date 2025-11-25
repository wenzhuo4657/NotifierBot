package cn.wenzhuo4657.noifiterBot.app.domain.notifier.model.vo;


import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.email.EmailNotifier;
import cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy.email.GmailConfig;

public enum ConfigType {
    // 配置类型
    GEMAIL("谷歌邮箱通知配置", "gmail", GmailConfig.class, EmailNotifier.class),
    ;

    String name;
    String tag;
    Class config;
    Class strategy;

    ConfigType(String name, String tag, Class config, Class strategy) {
        this.name = name;
        this.tag = tag;
        this.config = config;
        this.strategy = strategy;
    }

    public String getName() {
        return name;
    }

    public String getTag() {
        return tag;
    }

    public Class getConfig() {
        return config;
    }

    public Class getStrategy() {
        return strategy;
    }

    public static ConfigType fromTag(String tag) {
        for (ConfigType type : values()) {
            if (type.getTag().equals(tag)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown config type: " + tag);
    }
}
