package com.lifestorm.learn.template.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * Created by life_storm on 2018/5/4.
 */
public class FreeMarkerUtils {
    public Template getTemplate(String name){
        Configuration cfg = new Configuration();
        Template template = null;
        try {
            cfg.setDirectoryForTemplateLoading(new File("D:\\git\\learn\\src\\main\\java\\com\\lcw\\learn\\template\\freemarker\\ftl"));
            template = cfg.getTemplate(name);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return template;
    }

    public void print(String name,Map<String,Object> root){
        Template template = getTemplate(name);
        try {
            template.process(root, new PrintWriter(System.out));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fprint(String name,Map<String,Object> root,String outFileName){
        FileWriter fileWrite = null;
        try {
            fileWrite = new FileWriter(outFileName);
            Template template = getTemplate(name);
            template.process(root, fileWrite);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fileWrite != null){
                try {
                    fileWrite.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
