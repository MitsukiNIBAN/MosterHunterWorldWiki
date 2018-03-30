package com.satou.wiki.data.entity;

/**
 * Created by Mitsuki on 2018/3/30.
 */

public class MessageEvent<T> {
    private T content;
    private int id;

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
