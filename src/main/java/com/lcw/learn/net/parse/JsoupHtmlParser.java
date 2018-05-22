package com.lcw.learn.net.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

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
        Document doc = Jsoup.connect("https://nuoha.com/book/168270.html").get();
        Elements newsHeadline = doc.select("body > div.book > div.box01 > div.left > div.chapter > div > ul > li > a");

        //一切都是最好的安排 - 诺哈网
        String bookName = doc.title();
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("D://",bookName+".txt")));
        if(newsHeadline!=null && newsHeadline.size() > 0) {
            int size = newsHeadline.size();
            for (int i = size-1 ; i >= 0; i--){
                Element element = newsHeadline.get(i);
                String title = element.text();
                bufferedWriter.write(title);
                bufferedWriter.newLine();
                String url = element.absUrl("href");
                Document document = Jsoup.connect(url).get();

                Elements newsHeadlines = document.select("body > div.book > div.box01 > div.left > div.chapter > div.row02");
                if(newsHeadlines!=null && newsHeadlines.size() > 0){
                    Element textEle = newsHeadlines.get(0);
                    String content = textEle.text();
                    bufferedWriter.write(content);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
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
