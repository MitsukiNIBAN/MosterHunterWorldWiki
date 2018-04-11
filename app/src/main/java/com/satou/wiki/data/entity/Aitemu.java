package com.satou.wiki.data.entity;

import java.util.List;

/**
 * Created by Mitsuki on 2018/4/11.
 */

public class Aitemu{
    private String name;
    private String rare;
    private String carry;
    private String price;
    private String info;
    private List<Unit> data;

    public String getName() {
        return name+"";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRare() {
        return rare+"";
    }

    public void setRare(String rare) {
        this.rare = rare;
    }

    public String getCarry() {
        return carry+"";
    }

    public void setCarry(String carry) {
        this.carry = carry;
    }

    public String getPrice() {
        return price+"";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info+"";
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public List<Unit> getData() {
        return data;
    }

    public void setData(List<Unit> data) {
        this.data = data;
    }
}
