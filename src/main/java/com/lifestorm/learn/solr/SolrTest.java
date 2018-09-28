package com.lifestorm.learn.solr;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by life_storm on 2018/5/4.
 */
public class SolrTest {
    //solr 服务器地址
    public static final String solrServerUrl = "http://localhost:8983/solr";
    //solrhome下的core
    public static final String solrCroeHome = "my_solr";
    //待索引、查询字段
    public static String[] docs = {"Solr是一个独立的企业级搜索应用服务器",
            "它对外提供类似于Web-service的API接口",
            "用户可以通过http请求",
            "向搜索引擎服务器提交一定格式的XML文件生成索引",
            "也可以通过Http Get操作提出查找请求",
            "并得到XML格式的返回结果"};
    public static void main(String[] args) {
        SolrClient client = getSolrClient();
        int i=0;
        List<SolrInputDocument> solrDocs = new ArrayList<SolrInputDocument>();
        for (String content : docs) {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("id", i++);
            doc.addField("content_test", content);
            solrDocs.add(doc);
        }
        try {
            client.add(solrDocs);
            client.commit();
        } catch (SolrServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static SolrClient getSolrClient(){
        return new HttpSolrClient(solrServerUrl+"/"+solrCroeHome);
    }
}
