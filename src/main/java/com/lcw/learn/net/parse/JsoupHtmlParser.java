package com.lcw.learn.net.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by leiconwu on 2018/5/18.
 *
 * @author leicongwu
 *         since 1.0
 *         Modification History:
 *         Date         Author      Version     Description
 *         ------------------------------------------------------------------
 */
public class JsoupHtmlParser {

    public static void main(String[] args) throws Exception {
        Document doc = Jsoup.connect("https://nuoha.com/book/168270/00001.html").get();
        log(doc.title());
        body > div.book > div.box01 > div.recom > ul > li:nth-child(1)

        Elements newsHeadlines = doc.select("body > div.book > div.box01 > div.left > div.chapter > div.row02");
        for (Element headline : newsHeadlines) {
            log(headline.text());
        }
    }

    public static void parserText(){

    }

    public static void log(String  text){
        System.out.println(text);
    }

    public static void log(String  format,String... args){
        System.out.println(String.format(format,args));
    }
}
