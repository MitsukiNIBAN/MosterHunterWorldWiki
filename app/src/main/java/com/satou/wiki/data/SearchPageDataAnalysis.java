package com.satou.wiki.data;

import com.satou.wiki.adapter.ModuleListAdapter;
import com.satou.wiki.data.entity.Unit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mitsuki on 2018/4/2.
 */

public class SearchPageDataAnalysis {

    public static List<Unit> getSearchData(String str) {
        List<Unit> unitList = new ArrayList<>();

        Element content = getContent(str);

        Elements blocks = content.getElementsByClass("block");
        for (Element block : blocks) {
            if (block.children().attr("class").equals("no-result")) break;
            if (block.getElementsByClass("head").first() != null) continue;
            Element title = block.getElementsByClass("sub-head").first();
            Unit unit = new Unit();
            unit.setContent(title.text() + "");
            unit.setTitle(title.text() + "");
            unit.setItemType(ModuleListAdapter.FIRST_LEVEL);
            unitList.add(unit);
            for (Element a : block.getElementsByTag("a")) {
                Unit u = new Unit();
                u.setContent(a.text() + "");
                u.setItemType(ModuleListAdapter.CONTENT);
                u.setUrl(a.attr("href") + "");
                u.setTag(title.text() + "");
                unitList.add(u);
            }
        }

        return unitList;
    }

    private static Element getContent(String str) {
        Document doc = Jsoup.parse(str);
        Element body = doc.body();
        return body.getElementsByClass("main").first()
                .getElementsByClass("content").first();
    }
}
