package cn.wenzhuo4657.noifiterBot.app.domain.notifier.service.strategy;

import java.io.File;

public class NotifierMessage {


    private String title;

    private String content;

    private File file; // 附件


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
}
