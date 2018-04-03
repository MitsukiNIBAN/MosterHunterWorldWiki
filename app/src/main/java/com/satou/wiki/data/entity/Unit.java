package com.satou.wiki.data.entity;

/**
 * Created by Mitsuki on 2018/4/2.
 */

public class Unit {
    private String time;
    private String content;
    private String url;
    private String tag;
    private String title;
    private int itemType;

    public String getTime() {
        return time + "";
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content + "";
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url + "";
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTag() {
        return tag + "";
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title + "";
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", url='" + url + '\'' +
                ", tag='" + tag + '\'' +
                ", title='" + title + '\'' +
                ", itemType=" + itemType +
                '}';
    }
}
