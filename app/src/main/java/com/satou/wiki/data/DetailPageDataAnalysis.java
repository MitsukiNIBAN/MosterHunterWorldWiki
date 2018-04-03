package com.satou.wiki.data;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by Mitsuki on 2018/4/3.
 */

public class DetailPageDataAnalysis {

    public static String getDetail(String str) {
        Element content = getContent(str);
        Elements table = content.getElementsByClass("item-table");
        Elements grids = content.getElementsByClass("grids");
        Elements st = content.getElementsByClass("simple-table");
        Elements row = content.getElementsByClass("row");
        Elements description = content.getElementsByClass("description");
        String html = "";
        if (table != null && table.first() != null){
            html = html + "</br>" + table.first().html();
        }
        if (row != null && row.first() != null){
            html = html + "</br>" + row.first().html();
        }
        if (description != null && description.first() != null){
            html = html + "</br>" + description.first().html();
        }
        if (grids != null){
            for (Element g : grids){
                html = html + "</br>" + g.html();
            }
        }
        if (st != null){
            for (Element s : st){
                html = html + "</br>" + s.html();
            }
        }
        return html;
    }

    private static Element getContent(String str) {
        Document doc = Jsoup.parse(str);
        Element body = doc.body();
        return body.getElementsByClass("main").first()
                .getElementsByClass("content").first();
    }
}
