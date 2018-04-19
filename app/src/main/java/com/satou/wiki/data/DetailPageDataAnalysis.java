package com.satou.wiki.data;

import android.util.Log;

import com.satou.wiki.adapter.AitemuAdapter;
import com.satou.wiki.adapter.ModuleListAdapter;
import com.satou.wiki.data.entity.Aitemu;
import com.satou.wiki.data.entity.Buki;
import com.satou.wiki.data.entity.Sharpness;
import com.satou.wiki.data.entity.Unit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

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
        List<Unit> unitList = new ArrayList<>();
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

        if (content.getElementsByClass("grids").first() != null) {
            Elements items = content.getElementsByClass("grids").first()
                    .getElementsByClass("item");

            for (Element element : items) {
                Unit unit1 = new Unit();
                Element th = element.getElementsByTag("th").first();
                unit1.setItemType(AitemuAdapter.TITLE);
                unit1.setContent(th.text());
                unitList.add(unit1);
                Unit unit2 = new Unit();
                Element td = element.getElementsByTag("td").first();
                unit2.setItemType(AitemuAdapter.CONTENT);
                String s = td.text();
                s = s.replace("%", "%\n");
                s = s.replace("個", "個\n");
                s = s.replace("採集點", "採集\n");
                s = s.replace("採集", "採集\n");
                unit2.setContent(s);
//                Log.e("getAitemu", unit2.getContent());
                unitList.add(unit2);
            }

            aitemu.setData(unitList);
        }


        return aitemu;
    }

    public static Buki getBuki(String str) {
        Buki buki = new Buki();
        List<Unit> unitList = new ArrayList<>();
        Element content = getContent(str);
        Elements table = content.getElementsByClass("simple-table");

        Element baseInfo = table.first();
        Elements tr = baseInfo.getElementsByTag("tr");
        buki.setAttr("-");
        for (Element item : tr) {
            if (item.getElementsByTag("th").first().text().equals("名")) {
                buki.setName(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("稀有度")) {
                buki.setRare(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("鑲嵌槽")) {
                buki.setSet(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("攻擊")) {
                buki.setAttack(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("會心")) {
                buki.setCrit(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("防禦")) {
                buki.setDefense(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("屬性")) {
                buki.setAttr(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("系統")) {
                buki.setBug(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("炮擊")) {
                buki.setShelling(item.getElementsByTag("td").first().text());
            }
            if (item.getElementsByTag("th").first().text().equals("裝著瓶")) {
                String bottleStr = "";
                for (Element bottle : item.getElementsByTag("td").first().getElementsByClass("l1")) {
                    bottleStr = bottleStr + " " + bottle.text();
                }
                buki.setBottle(bottleStr);
            }
            if (item.getElementsByTag("th").first().text().equals("音色")) {
                String timbreStr = "";
                for (Element timbre : item.getElementsByTag("span")) {
                    timbreStr = timbreStr + " " + timbre.text();
                }
                buki.setTimbre(timbreStr);
            }
            if (item.getElementsByTag("th").first().text().equals("銳利度")) {
                Elements sharp = item.getElementsByClass("sharpness");
                List<Sharpness> sharpnessList = new ArrayList<>();
                for (int i = 0; i < sharp.size(); i++) {
                    Sharpness sharpness = new Sharpness();
                    sharpness.setName("匠+" + i);
                    String sharpnessStr = "";
                    for (Element element : sharp.get(i).getElementsByTag("div")) {
                        if (element.html().length() > 0) continue;
                        sharpnessStr = sharpnessStr + element.attr("style") + ",";
                    }
                    sharpness.setSharpness(sharpnessStr);
                    sharpnessList.add(sharpness);
                }
                buki.setSharpness(sharpnessList);
            }
        }

        for (int i = 1; i < table.size(); i++){
            for (Element trIntable : table.get(i).getElementsByTag("tr")){
                Unit unit1 = new Unit();
                Element th = trIntable.getElementsByTag("th").first();
                unit1.setItemType(AitemuAdapter.TITLE);
                unit1.setContent(th.text());
                unitList.add(unit1);
                Unit unit2 = new Unit();
                Element td = trIntable.getElementsByTag("td").first();
                unit2.setItemType(AitemuAdapter.CONTENT);
                String s = td.text();
                unit2.setContent(s);
                unitList.add(unit2);
            }
        }
        buki.setDataList(unitList);
        return buki;
    }

    private static Element getContent(String str) {
        Document doc = Jsoup.parse(str);
        Element body = doc.body();
        return body.getElementsByClass("main").first()
                .getElementsByClass("content").first();
    }
}
