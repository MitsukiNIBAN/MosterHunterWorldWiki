package com.satou.wiki.data.entity;

/**
 * Created by Mitsuki on 2018/3/30.
 */

public class Individual {
    private int itemType;
    private String content;
    private String url;

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
