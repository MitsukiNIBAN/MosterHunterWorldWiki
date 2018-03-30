package com.satou.wiki.http;

/**
 * Created by Mitsuki on 2018/3/30.
 */

public class RequestMsg {
    private int code;
    private String data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
