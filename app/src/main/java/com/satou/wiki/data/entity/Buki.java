package com.satou.wiki.data.entity;

import java.util.List;

/**
 * Created by Mitsuki on 2018/4/18.
 */

public class Buki {
    private String name;
    private String rare;
    private String set;
    private String attack;
    private String crit;
    private String defense;
    private String attr;
    private List<Sharpness> sharpness;
    private String timbre;
    private String bullet;
    private String bug;
    private String bottle;
    private String shelling;
    private List<Unit> dataList;

    public String getName() {
        return name + "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRare() {
        return rare + "";
    }

    public void setRare(String rare) {
        this.rare = rare;
    }

    public String getSet() {
        return set + "";
    }

    public void setSet(String set) {
        this.set = set;
    }

    public String getAttack() {
        return attack + "";
    }

    public void setAttack(String attack) {
        this.attack = attack;
    }

    public String getCrit() {
        return crit + "";
    }

    public void setCrit(String crit) {
        this.crit = crit;
    }

    public String getDefense() {
        return defense + "";
    }

    public void setDefense(String defense) {
        this.defense = defense;
    }

    public String getAttr() {
        return attr + "";
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public List<Sharpness> getSharpness() {
        return sharpness;
    }

    public void setSharpness(List<Sharpness> sharpness) {
        this.sharpness = sharpness;
    }

    public String getTimbre() {
        return timbre + "";
    }

    public void setTimbre(String timbre) {
        this.timbre = timbre;
    }

    public String getBullet() {
        return bullet + "";
    }

    public void setBullet(String bullet) {
        this.bullet = bullet;
    }

    public String getBug() {
        return bug + "";
    }

    public void setBug(String bug) {
        this.bug = bug;
    }

    public String getBottle() {
        return bottle + "";
    }

    public void setBottle(String bottle) {
        this.bottle = bottle;
    }

    public String getShelling() {
        return shelling + "";
    }

    public void setShelling(String shelling) {
        this.shelling = shelling;
    }

    public List<Unit> getDataList() {
        return dataList;
    }

    public void setDataList(List<Unit> dataList) {
        this.dataList = dataList;
    }
}
