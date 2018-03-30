package com.satou.wiki.data.entity;

import java.util.List;

/**
 * Created by Mitsuki on 2018/3/30.
 */

public class WikiLog {
    private String title;
    private List<Message> messageLsit;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Message> getMessageLsit() {
        return messageLsit;
    }

    public void setMessageLsit(List<Message> messageLsit) {
        this.messageLsit = messageLsit;
    }
}
