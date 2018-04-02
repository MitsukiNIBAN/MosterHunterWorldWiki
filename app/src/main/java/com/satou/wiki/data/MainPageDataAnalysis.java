package com.satou.wiki.data;

import android.util.Log;

import com.satou.wiki.adapter.ModuleListAdapter;
import com.satou.wiki.data.entity.Unit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitsuki on 2018/3/30.
 */

public class MainPageDataAnalysis {

    private static List<Unit> wikiLog;
    private static List<Unit> contentList;
    private static Unit gameUpdate;
    private static List<Unit> titleList;

    public static void initMainData(String str) {
        if (wikiLog == null) {
            wikiLog = new ArrayList<>();
        } else {
            wikiLog.clear();
        }
        if (contentList == null) {
            contentList = new ArrayList<>();
        } else {
            contentList.clear();
        }
        if (titleList == null) {
            titleList = new ArrayList<>();
        } else {
            titleList.clear();
        }
        if (gameUpdate == null) {
            gameUpdate = new Unit();
        }

        Element content = getContent(str);
        //解析wiki日志
        Elements msg = content.getElementsByClass("log").first()
                .getElementsByClass("message");
        for (Element m : msg) {
            Unit u = new Unit();
            u.setTime(m.getElementsByClass("time").first().text().toString() + "");
            u.setContent(m.getElementsByClass("msg-content").first().text().toString() + "");
            u.setItemType(ModuleListAdapter.CONTENT);
            wikiLog.add(u);
        }

        //解析游戏日志
        Element title = content.getElementsByClass("description game-update").first()
                .getElementsByClass("date").first();
        Element contentEle = content.getElementsByClass("description game-update").first()
                .getElementsByClass("content").first();
        gameUpdate.setTitle(title.text().toString() + "");
        gameUpdate.setContent(contentEle.text().toString() + "");
        gameUpdate.setItemType(ModuleListAdapter.CONTENT);

        //解析wiki栏目和其他
        Element grids = content.getElementsByClass("grids").first();
        Elements items = grids.getElementsByClass("item");
        for (Element item : items) {
            Elements t = item.getElementsByTag("th");
            getTitleTag(t);
        }

    }

    public static List<Unit> getWikiMsg() {
        return wikiLog;
    }

    public static Unit getGameUpdate() {
        return gameUpdate;
    }

    public static List<Unit> getTitleList(){
        return titleList;
    }

    private static Element getContent(String str) {
        Document doc = Jsoup.parse(str);
        Element body = doc.body();
        return body.getElementsByClass("main").first()
                .getElementsByClass("content").first();
    }

    private static void getTitleTag(Elements t) {
        if (t.size() == 1) {
            Unit unit = new Unit();
            unit.setContent(t.first().text());
            unit.setTitle(t.first().text());
            unit.setItemType(ModuleListAdapter.FIRST_LEVEL);
            titleList.add(unit);
        } else if (t.size() < 1) {
//            return "";
        } else {
            for (Element e : t) {
                Unit unit = new Unit();
                if (!e.attr("class").equals("sub-head")) {
                    if (e.parent().attr("class").equals("sub-head")) {
                        //三级title
                        unit.setItemType(ModuleListAdapter.THIRD_LEVEL);
                    } else {
                        //一级title
                        unit.setItemType(ModuleListAdapter.FIRST_LEVEL);
                    }
                } else {
                    //二级title
                    unit.setItemType(ModuleListAdapter.SECOND_LEVEL);
                }
                unit.setContent(e.text());
                unit.setTitle(e.text());
                titleList.add(unit);
            }
        }
    }

}
