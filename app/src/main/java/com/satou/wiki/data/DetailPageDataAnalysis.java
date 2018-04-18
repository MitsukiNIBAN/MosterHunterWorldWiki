package com.satou.wiki.data;

import com.satou.wiki.data.entity.Aitemu;
import com.satou.wiki.data.entity.Buki;

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
        if (table != null && table.first() != null) {
            html = html + "</br>" + table.first().html();
        }
        if (row != null && row.first() != null) {
            html = html + "</br>" + row.first().html();
        }
        if (description != null && description.first() != null) {
            html = html + "</br>" + description.first().html();
        }
        if (grids != null) {
            for (Element g : grids) {
                html = html + "</br>" + g.html();
            }
        }
        if (st != null) {
            for (Element s : st) {
                html = html + "</br>" + s.html();
            }
        }
        return html;
    }

    public static Aitemu getAitemu(String str) {
        Aitemu aitemu = new Aitemu();
        Element content = getContent(str);
        Elements table = content.getElementsByClass("item-table");
        if (table != null && table.first() != null) {
            Elements tr = table.first().getElementsByTag("tr");
            for (int i = 0; i < tr.size(); i++) {
                switch (i) {
                    case 0:
                        Element span = tr.get(i).getElementsByTag("span").first();
                        if (span != null) {
                            aitemu.setName(span.text() + "");
                        } else {
                            aitemu.setName("-");
                        }
                        break;
                    case 1:
                        Elements td = tr.get(i).getElementsByTag("td");
                        for (int j = 0; j < td.size(); j++) {
                            switch (j) {
                                case 0:
                                    if (td.get(j) != null) {
                                        aitemu.setRare(td.get(j).text() + "");
                                    } else {
                                        aitemu.setRare("-");
                                    }
                                    break;
                                case 1:
                                    if (td.get(j) != null) {
                                        aitemu.setCarry(td.get(j).text() + "");
                                    } else {
                                        aitemu.setCarry("-");
                                    }
                                    break;
                                case 2:
                                    if (td.get(j) != null) {
                                        aitemu.setPrice(td.get(j).text() + "");
                                    } else {
                                        aitemu.setPrice("-");
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                        break;
                    case 2:
                        Element info = tr.get(i).getElementsByTag("td").first();
                        if (info != null) {
                            aitemu.setInfo(info.text() + "");
                        } else {
                            aitemu.setInfo("-");
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        return aitemu;
    }

    public static Buki getBuki(String str){
        Buki buki = new Buki();
        return buki;
    }

    private static Element getContent(String str) {
        Document doc = Jsoup.parse(str);
        Element body = doc.body();
        return body.getElementsByClass("main").first()
                .getElementsByClass("content").first();
    }
}
