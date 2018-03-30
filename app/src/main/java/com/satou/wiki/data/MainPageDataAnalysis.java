package com.satou.wiki.data;

import com.satou.wiki.data.entity.GameUpdate;
import com.satou.wiki.data.entity.Message;
import com.satou.wiki.data.entity.WikiLog;

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

    public static WikiLog getWikiMsg(String str) {
        WikiLog wikiLog = new WikiLog();
        Element title = getContent(str).getElementsByClass("log").first()
                .getElementsByClass("title").first();
        Elements msg = getContent(str).getElementsByClass("log").first()
                .getElementsByClass("message");
        wikiLog.setTitle(title.text().toString() + "");

        List<Message> messageList = new ArrayList<>();
        for (Element m : msg) {
            Message message = new Message();
            message.setTime(m.getElementsByClass("time").first().text().toString() + "");
            message.setMsgContent(m.getElementsByClass("msg-content").first().text().toString() + "");
            messageList.add(message);
        }
        wikiLog.setMessageLsit(messageList);
        return wikiLog;
    }

    public static GameUpdate getGameUpdate(String str) {
        GameUpdate gameUpdate = new GameUpdate();
        Element title = getContent(str).getElementsByClass("description game-update").first()
                .getElementsByClass("date").first();
        Element content = getContent(str).getElementsByClass("description game-update").first()
                .getElementsByClass("content").first();
        gameUpdate.setDate(title.text().toString() + "");
        gameUpdate.setContent(content.text().toString() + "");
        return gameUpdate;
    }

    private static Element getContent(String str) {
        Document doc = Jsoup.parse(str);
        Element body = doc.body();
        return body.getElementsByClass("main").first()
                .getElementsByClass("content").first();
    }
}
