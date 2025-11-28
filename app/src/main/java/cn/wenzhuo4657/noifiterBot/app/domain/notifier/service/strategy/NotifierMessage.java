package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy;

import java.io.File;

public class NotifierMessage {


    private String title;

    private String content;

    private String file; // 附件（可选）




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

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
