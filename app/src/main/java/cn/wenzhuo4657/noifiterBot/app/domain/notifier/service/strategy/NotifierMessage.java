package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy;

import java.io.File;

public class NotifierMessage {


    private String title;

    private String content;

    private File file; // 附件（可选）


    private  String dynamicConfigJson;//可选字段，他用于适配不同通知器所需要的动态参数


    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getDynamicConfigJson() {
        return dynamicConfigJson;
    }

    public void setDynamicConfigJson(String dynamicConfigJson) {
        this.dynamicConfigJson = dynamicConfigJson;
    }
}
