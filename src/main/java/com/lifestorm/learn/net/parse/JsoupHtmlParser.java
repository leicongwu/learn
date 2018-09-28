package com.lifestorm.learn.net.parse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Created by leiconwu on 2018/5/18.
 *
 * @author life_storm
 *         since 1.0
 *         Modification History:
 *         Date         Author      Version     Description
 *         ------------------------------------------------------------------
 *         仅限个人使用
 */
public class JsoupHtmlParser {

    public static void main(String[] args) throws Exception {
        new JsoupHtmlParser().downSogouBookWeb();
    }

    public static void parserText(){

    }


    /**
     *搜狗小说下载
     * @throws Exception
     */
    public void downSogouBookWeb() throws Exception{
        Document doc = Jsoup.connect("https://m.sgxsw.com/sougou/5/5875/").get();
        //获取章节目录
        //获取页码数，进行下一页面章节查找
        //各章节内容
        long start_time = System.currentTimeMillis();
        Elements newsHeadline = doc.select("body > div.books > div.listpage > span.middle > select > option");
        String bookName = doc.title().split("最新章节列表")[0];
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("D://",bookName+".txt")));
        if(newsHeadline!=null && newsHeadline.size() > 0) {
            int size = newsHeadline.size();
            for (int i = 0 ; i < size ; i++){
                Element element = newsHeadline.get(i);
                //获得各个列表的url链接
                String url = element.absUrl("value");
                //获得列表
                Document document = Jsoup.connect(url).get();
                //获得具体的 body > div.books > div:nth-child(8) > dl > dd:nth-child(2) > a
                Elements newsHeadlines = document.select(" body > div.books > div:nth-child(8) > dl > dd > a");
                if(newsHeadlines!=null &&  newsHeadlines.size() > 0){
                int bookSize = newsHeadlines.size();
                    for (int j = 0 ; j < bookSize ; j++){
                        Element bookEle = newsHeadlines.get(j);
                        String realUrl = bookEle.absUrl("href");
                        String bookTitle = bookEle.html();
                        bufferedWriter.write(bookTitle);
                        bufferedWriter.newLine();
                        try {
                            //获得列表
                            Document bookDocument = Jsoup.connect(realUrl).timeout(10000).get();
                            String bookSelector = "#chaptercontent";
                            Elements newsBookHeadlines = bookDocument.select(bookSelector);
                            String content = newsBookHeadlines.text();
                            bufferedWriter.write(content);
                            bufferedWriter.newLine();
                            bufferedWriter.flush();
                        }catch (Exception ex){
                            System.out.println(bookTitle+"文章读取失败"+ex.getMessage());
                        }finally{
                            System.out.println(bookTitle+"文章已读"+new Date());
                        }
                    }

                }
            }
            bufferedWriter.flush();
            bufferedWriter.close();
            long end_time = System.currentTimeMillis();
            System.out.println("文章工用时"+(end_time-start_time)/1000+"秒");
        }
    }

    /**
     * 诺哈网书籍下载
     * @throws Exception
     */
    public void downNuoHaWeb() throws Exception{
        Document doc = Jsoup.connect("https://nuoha.com/book/48088.html").get();
        Elements newsHeadline = doc.select("body > div.book > div.box01 > div.left > div.chapter > div > ul > li > a");


        String bookName = doc.title().split("-")[0];
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

    /**
     * 记忆力书籍下载
     * @throws Exception
     */
    public void downJyliWeb() throws Exception{
        Document doc = Jsoup.connect("http://book.jiyili.net/0/58/index.html").get();
        Elements newsHeadline = doc.select("#wp > div > div.novel_volume > div.novel_list > ul > li > a");
        String bookName = doc.title().split("-")[0];
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(new File("D://",bookName+".txt")));
        if(newsHeadline!=null && newsHeadline.size() > 0) {
            int size = newsHeadline.size();
            for (int i = 0 ; i < size; i++){
                Element element = newsHeadline.get(i);
                String title = element.text();
                bufferedWriter.write(title);
                bufferedWriter.newLine();
                String url = element.absUrl("href");
                Document document = Jsoup.connect(url).get();

                Elements newsHeadlines = document.select("#content > div.novel_content");
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

    /**
     * 获取必要的登陆参数信息
     *
     * @throws IOException
     */
    private void fetchNecessaryParam() throws IOException {

    }


    public void parseShopWeb() throws Exception{
        String loginUrl = "http://lhxs.jngcxh.com/jsp/portal/login.jsp";

        Document doc = Jsoup.connect("https://nuoha.com/book/48088.html").get();
        Elements newsHeadline = doc.select("body > div.book > div.box01 > div.left > div.chapter > div > ul > li > a");


        String bookName = doc.title().split("-")[0];
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

    public static void log(String  text){
        System.out.println(text);
    }

    public static void log(String  format,String... args){
        System.out.println(String.format(format,args));
    }
}
